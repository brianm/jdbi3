package org.jdbi;

public interface Mappy<ResultType, A>
{
    public ResultType apply(final A a);
}
