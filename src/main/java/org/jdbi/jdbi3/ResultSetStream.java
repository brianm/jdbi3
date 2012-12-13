package org.jdbi.jdbi3;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;
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
            private boolean advanced = false;
            private boolean next = false;

            @Override
            public boolean hasNext()
            {
                if (advanced) {
                    return next;
                }
                try {
                    advanced = true;
                    next = results.next();

                    if (next == false) {
                        close();
                    }

                    return next;
                }
                catch (SQLException e) {
                    throw new JDBIException(e);
                }
            }
            public void close()
            {
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
                if (hasNext()) {
                    advanced = false;
                    return new ResultSetRow(results);
                }
                else {
                    throw new NoSuchElementException();
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
