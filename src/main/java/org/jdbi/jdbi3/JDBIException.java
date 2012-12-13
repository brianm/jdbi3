package org.jdbi.jdbi3;

public class JDBIException extends RuntimeException
{
    public JDBIException(Throwable cause)
    {
        super(cause);
    }
}
