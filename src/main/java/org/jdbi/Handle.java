package org.jdbi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Handle
{
    private final Connection conn;

    Handle(final Connection c)
    {
        conn = c;
    }

    public Query select(final String query)
    {
        return new Query(query, conn);
    }
}
