package org.jdbi.jdbi3;

public interface Mappy<ResultType, A>
{

    public ResultType apply(final A a);
}
