package org.jdbi.jdbi3;

import org.h2.jdbcx.JdbcConnectionPool;
import org.jdbi.JDBI;
import org.junit.rules.ExternalResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class H2Rule extends ExternalResource
{
    private final List<String> fixtures;
    private JdbcConnectionPool ds;
    private JDBI jdbi;

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
        jdbi = new JDBI(ds);
        jdbi.execute(h -> {
            for (String fixture : fixtures) {
                h.execute(fixture);
            }
        });
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

    public JDBI getJdbi()
    {
        return jdbi;
    }




    public H2Rule withFixture(final String s)
    {
        List<String> fixes = new ArrayList<>(fixtures.size() + 1);
        fixes.addAll(this.fixtures);
        fixes.add(s);
        return new H2Rule(fixes);
    }
}
