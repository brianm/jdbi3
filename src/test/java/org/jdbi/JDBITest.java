package org.jdbi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class JDBITest
{
    @Rule
    public H2Rule h2 = new H2Rule().withFixture("create table something (id integer primary key, name varchar)")
                                   .withFixture("insert into something (id, name) values (1, 'Brian'), (2, 'Steven')");

    @Test
    public void testWithHandleFunctionForm() throws Exception
    {
        JDBI db = new JDBI(h2);
        List<String> names = db.open(h -> h.query("select name from something order by id")
                                           .stream()
                                           .map((r) -> r.getString(1))
                                           .collect(Collectors.toList()));
        assertThat(names).isEqualTo(ImmutableList.of("Brian", "Steven"));
    }

    @Test
    public void testWithHandleConsumerForm() throws Exception
    {
        JDBI db = new JDBI(h2);
        Set<String> names = Sets.newHashSet("Brian", "Steven");
        db.run(h -> {
            assertThat(names).containsOnly("Brian", "Steven");
            h.query("select name from something order by id")
             .stream()
             .map((r) -> r.getString(1))
             .forEach(names::remove);
        });
        assertThat(names).isEmpty();
    }
}
