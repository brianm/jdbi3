package org.jdbi.jdbi3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;
import java.util.stream.Streams;

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
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            return Streams.stream(new ResultSetStream(stmt.executeQuery(), stmt), 0);
        }
        catch (SQLException e) {
            throw new JDBIException(e);
        }
    }

    public int execute(String sql, Object... args)
    {
        try {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i + 1, args[i]);
                }

                stmt.execute();
                return stmt.getUpdateCount();
            }
        }
        catch (SQLException e) {
            throw new JDBIException(e);
        }
    }
}
