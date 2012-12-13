package org.jdbi.jdbi3;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.function.Block;
import java.util.stream.Spliterator;
import java.util.stream.Streams;

public class ResultSetStream implements Spliterator<ResultSetRow>
{
    private final ResultSet results;
    private final PreparedStatement stmt;

    public ResultSetStream(ResultSet rs, PreparedStatement stmt)
    {
        this.results = rs;
        this.stmt = stmt;
    }

    @Override
    public int getNaturalSplits()
    {
        return 0;
    }

    @Override
    public Spliterator<ResultSetRow> split()
    {
        return Streams.emptySpliterator();
    }

    @Override
    public Iterator<ResultSetRow> iterator()
    {
        return new Iterator<ResultSetRow>()
        {
            private boolean closed = false;
            private boolean hasNext = false;
            private boolean alreadyAdvanced = false;

            @Override
            public boolean hasNext()
            {
                if (closed) {
                    return false;
                }

                if (alreadyAdvanced) {
                    return hasNext;
                }

                hasNext = safeNext();

                if (hasNext) {
                    alreadyAdvanced = true;
                }
                else {
                    close();
                }

                return hasNext;
            }

            private boolean safeNext()
            {
                try {
                    return results.next();
                }
                catch (SQLException e) {
                    throw new JDBIException(e);
                }
            }

            public void close()
            {
                closed = true;
                try {
                    results.close();
                    stmt.close();
                }
                catch (SQLException e) {
                    throw new JDBIException(e);
                }
            }


            @Override
            public ResultSetRow next()
            {
                if (closed) {
                    throw new IllegalStateException("iterator is closed");
                }

                if (!hasNext()) {
                    close();
                    throw new IllegalStateException("No element to advance to");
                }

                try {
                    return new ResultSetRow(results);
                }
                finally {
                    alreadyAdvanced = safeNext();
                    if (!alreadyAdvanced) {
                        close();
                    }
                }
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException("Deleting from a result set iterator is not yet supported");
            }

            @Override
            public void forEach(Block<? super ResultSetRow> block)
            {
                while (this.hasNext()) {
                    block.accept(this.next());
                }
            }
        };
    }

    @Override
    public boolean isPredictableSplits()
    {
        return false;
    }
}
