package org.jdbi.jdbi3;

import com.google.common.collect.ImmutableSet;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class JDBITest
{
    private JdbcConnectionPool ds;
    private JDBI jdbi;

    @Before
    public void setUp() throws Exception
    {
        ds = JdbcConnectionPool.create("jdbc:h2:mem:JDBITest", "", "");
        jdbi = new JDBI(ds);
        jdbi.withHandle(h -> h.execute("create table something (id int primary key, name varchar)"));
    }

    @After
    public void tearDown() throws Exception
    {
        ds.dispose();
    }

    @Test
    public void testInsert() throws Exception
    {
        jdbi.withHandle(h -> h.execute("insert into something (id, name) values (?, ?)", 1, "Brian"));
    }

    @Test
    public void testExample() throws Exception
    {
        Set<Something> things = jdbi.withHandle(h -> {
            h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");
            h.execute("insert into something (id, name) values (?, ?)", 2, "Steven");

            return h.query("select id, name from something")
                    .map(rs -> new Something(rs.getInt(1), rs.getString(2)))
                    .into(new HashSet<Something>());
        });

        assertThat(things).isEqualTo(ImmutableSet.of(new Something(1, "Brian"),
                                                     new Something(2, "Steven")));
    }
}
