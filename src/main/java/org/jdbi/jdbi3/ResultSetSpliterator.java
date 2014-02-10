package org.jdbi.jdbi3;

import java.util.Comparator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class ResultSetSpliterator extends Spliterators.AbstractSpliterator<ResultSetRow>
{
    /**
     * Creates a spliterator reporting the given estimated size and
     * additionalCharacteristics.
     *
     * @param est                       the estimated size of this spliterator if known, otherwise
     *                                  {@code Long.MAX_VALUE}.
     * @param additionalCharacteristics properties of this spliterator's
     *                                  source or elements.  If {@code SIZED} is reported then this
     *                                  spliterator will additionally report {@code SUBSIZED}.
     */
    protected ResultSetSpliterator(final long est, final int additionalCharacteristics)
    {
        super(Long.MAX_VALUE, additionalCharacteristics);
    }

    @Override
    public boolean tryAdvance(final Consumer<? super ResultSetRow> action)
    {
        return false;
    }

    @Override
    public void forEachRemaining(final Consumer<? super ResultSetRow> action)
    {
    }

    @Override
    public long getExactSizeIfKnown()
    {
        return 0;
    }

    @Override
    public boolean hasCharacteristics(final int characteristics)
    {
        return false;
    }

    @Override
    public Comparator<? super ResultSetRow> getComparator()
    {
        return null;
    }
}
