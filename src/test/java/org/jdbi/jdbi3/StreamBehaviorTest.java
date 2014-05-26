package org.jdbi.jdbi3;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.*;
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

    @Test
    public void testBar() throws Exception
    {
        List<String> ls = ImmutableList.of("Brian", "Henning", "Tom", "Adam", "Tatu", "Patrick");
        Spliterator<String> s = Spliterators.spliteratorUnknownSize(ls.iterator(), ORDERED | NONNULL | IMMUTABLE);
        Stream<String> stream = StreamSupport.stream(s, false);
        List<String> same = stream.collect(Collectors.toList());
        assertThat(same).isEqualTo(ls);

    }
}
