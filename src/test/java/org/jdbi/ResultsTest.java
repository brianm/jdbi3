package org.jdbi;

import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class ResultsTest
{
    @Rule
    public H2Rule h2 = new H2Rule().withFixture("create table something (id integer primary key, name varchar)")
                                   .withFixture("insert into something (id, name) values (1, 'Brian'), (2, 'Steven')");

    @Test
    public void testFoo() throws Exception
    {
        DataSource ds = h2.getDatasource();
        try (Connection c = ds.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement("select name from something order by id")) {
                try (ResultSet rs = ps.executeQuery()) {
                    UncheckedResultSet urs = Results.unchecked(rs);
                    List<String> names = urs.stream()
                                            .map((r) -> r.getString(1))
                                            .collect(Collectors.toList());
                    assertThat(names).isEqualTo(ImmutableList.of("Brian", "Steven"));
                }
            }
        }
    }
}
