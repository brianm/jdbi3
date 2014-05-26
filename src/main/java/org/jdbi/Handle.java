package org.jdbi;

import com.google.common.collect.AbstractIterator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    public Stream<ResultSetRow> query(String sql)
    {
        try
        {
            PreparedStatement stmt = connection.prepareStatement(sql);
            final ResultSet rs = stmt.executeQuery();

            Spliterator<ResultSetRow> split = Spliterators.spliteratorUnknownSize(new AbstractIterator<ResultSetRow>()
            {
                @Override
                protected ResultSetRow computeNext()
                {
                    try
                    {
                        if (rs.next()) {
                            return new ResultSetRow(rs);
                        }
                        else
                        {
                            rs.close();
                            return this.endOfData();
                        }
                    }
                    catch (SQLException e)
                    {
                        throw new UncheckedSQLException(e);
                    }

                }
            }, Spliterator.ORDERED);
            return StreamSupport.stream(split, false);
        }
        catch (SQLException e)
        {
            throw new UncheckedSQLException(e);
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
            throw new UncheckedSQLException(e);
        }
    }
}
