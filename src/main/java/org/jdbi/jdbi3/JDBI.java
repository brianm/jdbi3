package org.jdbi.jdbi3;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.function.Block;
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
}
