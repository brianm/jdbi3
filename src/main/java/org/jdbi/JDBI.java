package org.jdbi;

import com.fasterxml.classmate.MemberResolver;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.ResolvedTypeWithMembers;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.members.ResolvedMethod;
import com.google.common.collect.Iterables;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class JDBI
{
    private final DataSource ds;

    public JDBI(DataSource ds)
    {
        this.ds = ds;
    }

    public <T> T returning(Function<Handle, T> function)
    {
        try
        {
            try (Handle h = new Handle(ds.getConnection()))
            {
                return function.apply(h);
            }
        }
        catch (SQLException e)
        {
            throw new UncheckedSQLException(e);
        }
    }

    public void execute(Consumer<Handle> function)
    {
        try
        {
            try (Handle h = new Handle(ds.getConnection()))
            {
                function.accept(h);
            }
        }
        catch (SQLException e)
        {
            throw new UncheckedSQLException(e);
        }
    }

    public static <ResultType, A> Function<ResultSetRow, ResultType> extract(final Mappy<ResultType, A> mappy)
    {
        TypeResolver tr = new TypeResolver();
        ResolvedType rt = tr.resolve(mappy.getClass());
        MemberResolver mr = new MemberResolver(tr);
        ResolvedTypeWithMembers rtwm = mr.resolve(rt, null, null);

        ResolvedMethod rm = Iterables.getFirst(Iterables.filter(Arrays.asList(rtwm.getMemberMethods()), r -> "apply".equals(r.getName())), null);
        ResolvedType at =  rm.getArgumentType(0);
        System.out.println(at.getTypeName());

        return (row) -> {
            A id = (A) Integer.valueOf(row.getInt(1));
            ResultType rs = mappy.apply(id);
            return rs;
        };
    }

    public static <ResultType, A, B> Function<ResultSetRow, ResultType> decompose(final Mappy2<ResultType, A, B> mappy)
    {
        return row -> {
            A id = (A) Integer.valueOf(row.getInt(1));
            B name = (B) row.getString(2);
            return (ResultType) mappy.apply(id, name);
        };
    }
}
