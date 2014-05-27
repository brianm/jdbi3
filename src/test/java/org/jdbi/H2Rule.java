package org.jdbi;

import com.google.common.collect.ImmutableList;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.rules.ExternalResource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class H2Rule extends ExternalResource
{
    private final List<String> fixtures;
    private JdbcConnectionPool ds;

    public H2Rule(List<String> fixtures) {
        this.fixtures = fixtures;
    }

    public H2Rule() {
        this(Collections.emptyList());
    }

    @Override
    protected void before() throws Throwable
    {
        ds = JdbcConnectionPool.create("jdbc:h2:mem:" + UUID.randomUUID(), "", "");
        try (Connection c = ds.getConnection())
        {
            for (String fixture : fixtures) {
                try (PreparedStatement ps = c.prepareStatement(fixture))
                {
                    ps.execute();
                }
            }
        }
    }

    @Override
    protected void after()
    {
        ds.dispose();
    }

    public JdbcConnectionPool getDatasource()
    {
        return ds;
    }

    public H2Rule withFixture(final String s)
    {
        List<String> fixes = new ArrayList<>(fixtures.size() + 1);
        fixes.addAll(this.fixtures);
        fixes.add(s);
        return new H2Rule(fixes);
    }
}
