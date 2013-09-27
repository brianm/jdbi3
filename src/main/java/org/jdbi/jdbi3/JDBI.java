package org.jdbi.jdbi3;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.function.Function;

public class JDBI
{
    private final DataSource ds;

    public JDBI(DataSource ds)
    {
        this.ds = ds;
    }

    public <T> T withHandle(Function<Handle, T> function)
    {
        try {
            try (Handle h = new Handle(ds.getConnection())) {
                return function.apply(h);
            }
        }
        catch (SQLException e) {
            throw new JDBIException(e);
        }
    }

    public static <ResultType, A> Function<ResultSetRow, ResultType> extract(final Mappy<ResultType, A> mappy)
    {
        return (row) -> {
            A id = (A) Integer.valueOf(row.getInt(1));
            ResultType rs = mappy.apply(id);
            return rs;
        };
    }

    public static <ResultType, A> Function<ResultSetRow, ResultType> extract1(final Mappy<ResultType, A> mappy)
    {
        return extract(mappy);
    }

    public static <ResultType, A, B> Function<ResultSetRow, ResultType> extract(final Mappy2<ResultType, A, B> mappy)
    {
        return row -> {
            A id = (A) Integer.valueOf(row.getInt(1));
            B name = (B) row.getString(2);
            return (ResultType) mappy.apply(id, name);
        };
    }
}
