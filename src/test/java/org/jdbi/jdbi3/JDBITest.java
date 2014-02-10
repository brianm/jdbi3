package org.jdbi.jdbi3;

import com.google.common.collect.ImmutableSet;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class JDBITest
{
    private JdbcConnectionPool ds;
    private JDBI jdbi;

    @Before
    public void setUp() throws Exception
    {
        ds = JdbcConnectionPool.create("jdbc:h2:mem:" + UUID.randomUUID(), "", "");
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
                    .collect(Collectors.toSet());
        });

        assertThat(things).isEqualTo(ImmutableSet.of(new Something(1, "Brian"),
                                                     new Something(2, "Steven")));
    }

    @Test
    public void testExample2() throws Exception
    {
        Set<Integer> things = jdbi.withHandle(h -> {
            h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");
            h.execute("insert into something (id, name) values (?, ?)", 2, "Steven");

            return h.query("select id, name from something")
                    .map(JDBI.extract((Integer id) -> id))
                    .collect(Collectors.toSet());
        });

        assertThat(things).isEqualTo(ImmutableSet.of(1, 2));
    }

    @Test
    public void testExample3() throws Exception
    {
        Set<Something> things = jdbi.withHandle(h -> {
            h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");
            h.execute("insert into something (id, name) values (?, ?)", 2, "Steven");

            return h.query("select id, name from something")
                    .map(JDBI.extract((Integer id, String name) -> new Something(id, name)))
                    .collect(Collectors.toSet());
        });

        assertThat(things).isEqualTo(ImmutableSet.of(new Something(1, "Brian"), new Something(2, "Steven")));
    }

    @Test
    public void testExample4() throws Exception
    {
        Set<Something> things = jdbi.withHandle(h -> {
            h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");
            h.execute("insert into something (id, name) values (?, ?)", 2, "Steven");

            return h.query("select id, name from something")
                    .map(JDBI.extract(Something::new))
                    .collect(Collectors.toSet());
        });

        assertThat(things).isEqualTo(ImmutableSet.of(new Something(1, "Brian"), new Something(2, "Steven")));
    }

    public static Integer succ(Integer i)
    {
        return i + 1;
    }

    @Test
    public void testExample5() throws Exception
    {
        Set<Integer> things = jdbi.withHandle(h -> {
            h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");
            h.execute("insert into something (id, name) values (?, ?)", 2, "Steven");

            return h.query("select id from something")
                    .map(JDBI.extract1(JDBITest::succ))
                    .map(it -> {
                        System.out.println(it);
                        return it;
                    })
                    .collect(Collectors.toSet());
        });

        assertThat(things).isEqualTo(ImmutableSet.of(2, 3));
    }

}
