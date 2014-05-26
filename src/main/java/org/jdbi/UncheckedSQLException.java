package org.jdbi;

import java.sql.SQLException;

public class UncheckedSQLException extends RuntimeException
{
    public UncheckedSQLException(SQLException cause)
    {
        super(cause);
    }
}
