package org.jdbi.jdbi3;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class StreamBehaviorTest
{
    @Test
    public void testFoo() throws Exception
    {
        List<String> ls = ImmutableList.of("Brian", "Henning", "Tom", "Adam", "Tatu", "Patrick");
        ls.forEach(s -> {
            assertThat(ls).contains(s);
        });
    }
}
