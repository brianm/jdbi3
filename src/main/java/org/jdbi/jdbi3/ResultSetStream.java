package org.jdbi.jdbi3;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

public class ResultSetStream implements BaseStream<ResultSetRow, ResultSetStream>
{
    @Override
    public Iterator<ResultSetRow> iterator()
    {
        return null;
    }

    @Override
    public Spliterator<ResultSetRow> spliterator()
    {
        return null;
    }

    @Override
    public boolean isParallel()
    {
        return false;
    }

    @Override
    public ResultSetStream sequential()
    {
        return null;
    }

    @Override
    public ResultSetStream parallel()
    {
        return null;
    }

    @Override
    public ResultSetStream unordered()
    {
        return null;
    }

    @Override
    public ResultSetStream onClose(final Runnable closeHandler)
    {
        return null;
    }

    @Override
    public void close()
    {
    }
}
