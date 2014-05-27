package org.jdbi;

import java.sql.ResultSet;

/**
 * UncheckedResultSet which prevents changing position
 */
class ResultSetRow extends UncheckedResultSet
{
    ResultSetRow(final ResultSet rs)
    {
        super(rs);
    }

    @Override
    public boolean next()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public void close()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public void beforeFirst()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public void afterLast()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public boolean first()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public boolean last()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public boolean absolute(final int row)
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public boolean relative(final int rows)
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public boolean previous()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public void setFetchDirection(final int direction)
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public void insertRow()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public void moveToInsertRow()
    {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public void moveToCurrentRow()
    {
        throw new UnsupportedOperationException("Not Supported");
    }
}
