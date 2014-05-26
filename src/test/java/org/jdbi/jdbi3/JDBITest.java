package org.jdbi.jdbi3;

import com.google.common.collect.ImmutableSet;
import org.jdbi.JDBI;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;
import static org.jdbi.JDBI.decompose;
import static org.jdbi.JDBI.extract;

public class JDBITest
{

    @Rule
    public H2Rule h2 = new H2Rule().withFixture("create table something (id int, name varchar)");

    private JDBI jdbi;

    @Before
    public void setUp() throws Exception
    {
        this.jdbi = h2.getJdbi();
    }

    @Test
    public void testInsert() throws Exception
    {
        jdbi.execute(h -> h.execute("insert into something (id, name) values (?, ?)", 1, "Brian"));
    }

    @Test
    public void testExample() throws Exception
    {
        Set<Something> things = jdbi.returning(h -> {
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
        Set<Integer> things = jdbi.returning(h -> {
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
        Set<Something> things = jdbi.returning(h -> {
            h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");
            h.execute("insert into something (id, name) values (?, ?)", 2, "Steven");

            return h.query("select id, name from something")
                    .map(decompose((Integer id, String name) -> new Something(id, name)))
                    .collect(Collectors.toSet());
        });

        assertThat(things).isEqualTo(ImmutableSet.of(new Something(1, "Brian"), new Something(2, "Steven")));
    }

    @Test
    public void testExample4() throws Exception
    {
        Set<Something> things = jdbi.returning(h -> {
            h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");
            h.execute("insert into something (id, name) values (?, ?)", 2, "Steven");

            return h.query("select id, name from something")
                    .map(decompose(Something::new))
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
        Set<Integer> things = jdbi.returning(h -> {
            h.execute("insert into something (id, name) values (?, ?)", 1, "Brian");
            h.execute("insert into something (id, name) values (?, ?)", 2, "Steven");

            return h.query("select id from something")
                    .map(extract(JDBITest::succ))
                    .map(it -> {
                        System.out.println(it);
                        return it;
                    })
                    .collect(Collectors.toSet());
        });

        assertThat(things).isEqualTo(ImmutableSet.of(2, 3));
    }

}
