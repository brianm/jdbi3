package org.jdbi.jdbi3;

public interface Mappy2<ResultType, A, B>
{
    public ResultType apply(A a, B b);
}
