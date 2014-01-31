package org.jdbi.jdbi3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Handle implements AutoCloseable
{
    private final Connection connection;

    public Handle(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void close() throws SQLException
    {
        connection.close();
    }


    public Stream<ResultSetRow> query(String sql, Object... args)
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            final ResultSet rs = stmt.executeQuery();
            Iterator<ResultSetRow> itty = new Iterator<ResultSetRow>()
            {

                private boolean advanced = false;
                private boolean next = false;

                @Override
                public boolean hasNext()
                {
                    if (advanced) {
                        return next;
                    }
                    else
                    {
                        advanced = true;
                        try
                        {
                            next = rs.next();
                            return next;
                        }
                        catch (SQLException e)
                        {
                            throw new UnsupportedOperationException("Not Yet Implemented!");
                        }
                    }
                }

                @Override
                public ResultSetRow next()
                {
                    if (!hasNext()) {
                        throw new IllegalStateException("nothing to traverse to!");
                    }
                    advanced = false;
                    return new ResultSetRow(rs);
                }
            };
            Spliterator<ResultSetRow> split = Spliterators.spliteratorUnknownSize(itty,
                                                                                  Spliterator.NONNULL | Spliterator.ORDERED);
            return StreamSupport.stream(split, false);
        }
        catch (SQLException e)
        {
            throw new JDBIException(e);
        }
    }

    public int execute(String sql, Object... args)
    {
        try
        {
            try (PreparedStatement stmt = connection.prepareStatement(sql))
            {

                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i + 1, args[i]);
                }

                stmt.execute();
                return stmt.getUpdateCount();
            }
        }
        catch (SQLException e)
        {
            throw new JDBIException(e);
        }
    }
}
