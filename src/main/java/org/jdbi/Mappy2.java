package org.jdbi;

public interface Mappy2<ResultType, A, B>
{
    public ResultType apply(A a, B b);
}
