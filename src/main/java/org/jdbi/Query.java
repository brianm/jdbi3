package org.jdbi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;
import java.util.stream.Stream;

class Query implements Supplier<Stream<UncheckedResultSet>>
{
    private final String query;
    private final Connection conn;

    public Query(final String query, final Connection conn)
    {

        this.query = query;
        this.conn = conn;
    }

    public Stream<UncheckedResultSet> stream() {
        return get();
    }

    @Override
    public Stream<UncheckedResultSet> get()
    {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            UncheckedResultSet urs = new UncheckedResultSet(rs);
            urs.whenFinished(rs::close);
            urs.whenFinished(stmt::close);

            return urs.stream();
        }
        catch (SQLException e) {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e1) {
                    // nothing to do :-(
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e1) {
                    // nothing to do :-(
                }
            }
            throw new UncheckedSQLException(e);
        }
    }
}
