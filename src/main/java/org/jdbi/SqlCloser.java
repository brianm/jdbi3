package org.jdbi;

import java.sql.SQLException;

public interface SqlCloser
{
    public void close() throws SQLException;
}
