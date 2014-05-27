package org.jdbi;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class JDBI
{
    private final DataSource ds;

    public JDBI(DataSource ds)
    {
        this.ds = ds;
    }

    public <ResultType> ResultType open(Function<Handle, ResultType> f)
    {
        try {
            try (Connection c = ds.getConnection()) {
                return f.apply(new Handle(c));
            }
        }
        catch (SQLException e) {
            // TODO exception
            throw new UnsupportedOperationException("Not Yet Implemented!");
        }
    }

    public void run(Consumer<Handle> f)
    {
        open((Handle h) -> {
            f.accept(h);
            return null;
        });
    }
}
