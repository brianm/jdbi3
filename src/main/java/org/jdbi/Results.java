package org.jdbi;

import com.google.common.collect.AbstractIterator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;

public class Results
{
    public static UncheckedResultSet unchecked(ResultSet rs) {
        return new UncheckedResultSet(rs);
    }

    public static Stream<ResultSet> stream(ResultSet rs) {
        Iterator<ResultSet> itty = new AbstractIterator<ResultSet>()
        {
            @Override
            protected ResultSet computeNext()
            {
                try
                {
                    if (rs.next()) {
                        return rs;
                    }
                    else {
                        return this.endOfData();
                    }
                }
                catch (SQLException e)
                {
                    throw new UncheckedSQLException(e);
                }
            }
        };
        Spliterator<ResultSet> split =  Spliterators.spliteratorUnknownSize(itty, ORDERED | NONNULL);
        return StreamSupport.stream(split, false);
    }
}
