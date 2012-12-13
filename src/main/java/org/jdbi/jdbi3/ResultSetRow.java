package org.jdbi.jdbi3;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetRow
{
    private final ResultSet rs;

    public ResultSetRow(ResultSet rs) {

        this.rs = rs;
    }

    public int getInt(int index)
    {
        try {
            return rs.getInt(index);
        }
        catch (SQLException e) {
            throw new JDBIException(e);
        }
    }

    public String getString(int index)
    {
        try {
            return rs.getString(index);
        }
        catch (SQLException e) {
            throw new JDBIException(e);
        }
    }
}
