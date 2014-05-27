package org.jdbi;

import com.google.common.collect.AbstractIterator;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;

class UncheckedResultSet implements ResultSet
{
    private final ResultSet rs;
    private final List<Consumer<SQLException>> handlers = new ArrayList<>();

    public UncheckedResultSet(ResultSet rs) {
        this.rs = rs;
    }


    public UncheckedResultSet onException(Consumer<SQLException> handler) {
        handlers.add(handler);
        return this;
    }

    public UncheckedResultSet onException(Runnable handler) {
        handlers.add((_e) -> handler.run());
        return this;
    }

    public Stream<UncheckedResultSet> stream() {
        Iterator<UncheckedResultSet> itty = new AbstractIterator<UncheckedResultSet>()
        {
            @Override
            protected UncheckedResultSet computeNext()
            {
                try
                {
                    if (rs.next()) {
                        return new ResultSetRow(rs);
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
        Spliterator<UncheckedResultSet> split =  Spliterators.spliteratorUnknownSize(itty, ORDERED | NONNULL);
        return StreamSupport.stream(split, false);
    }

    @Override
    public boolean next()
    {
        try { return rs.next(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void close()
    {
        try { rs.close(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean wasNull()
    {
        try { return rs.wasNull(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public String getString(final int columnIndex)
    {
        try { return rs.getString(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean getBoolean(final int columnIndex)
    {
        try { return rs.getBoolean(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public byte getByte(final int columnIndex)
    {
        try { return rs.getByte(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public short getShort(final int columnIndex)
    {
        try { return rs.getShort(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int getInt(final int columnIndex)
    {
        try { return rs.getInt(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public long getLong(final int columnIndex)
    {
        try { return rs.getLong(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public float getFloat(final int columnIndex)
    {
        try { return rs.getFloat(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public double getDouble(final int columnIndex)
    {
        try { return rs.getDouble(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public BigDecimal getBigDecimal(final int columnIndex, final int scale)
    {
        try { return rs.getBigDecimal(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public byte[] getBytes(final int columnIndex)
    {
        try { return rs.getBytes(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Date getDate(final int columnIndex)
    {
        try { return rs.getDate(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Time getTime(final int columnIndex)
    {
        try { return rs.getTime(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Timestamp getTimestamp(final int columnIndex)
    {
        try { return rs.getTimestamp(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public InputStream getAsciiStream(final int columnIndex)
    {
        try { return rs.getAsciiStream(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public InputStream getUnicodeStream(final int columnIndex)
    {
        try { return rs.getUnicodeStream(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public InputStream getBinaryStream(final int columnIndex)
    {
        try { return rs.getBinaryStream(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public String getString(final String columnLabel)
    {
        try { return rs.getString(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean getBoolean(final String columnLabel)
    {
        try { return rs.getBoolean(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public byte getByte(final String columnLabel)
    {
        try { return rs.getByte(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public short getShort(final String columnLabel)
    {
        try { return rs.getShort(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int getInt(final String columnLabel)
    {
        try { return rs.getInt(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public long getLong(final String columnLabel)
    {
        try { return rs.getLong(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public float getFloat(final String columnLabel)
    {
        try { return rs.getFloat(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public double getDouble(final String columnLabel)
    {
        try { return rs.getDouble(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public BigDecimal getBigDecimal(final String columnLabel, final int scale)
    {
        try { return rs.getBigDecimal(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public byte[] getBytes(final String columnLabel)
    {
        try { return rs.getBytes(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Date getDate(final String columnLabel)
    {
        try { return rs.getDate(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Time getTime(final String columnLabel)
    {
        try { return rs.getTime(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Timestamp getTimestamp(final String columnLabel)
    {
        try { return rs.getTimestamp(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public InputStream getAsciiStream(final String columnLabel)
    {
        try { return rs.getAsciiStream(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public InputStream getUnicodeStream(final String columnLabel)
    {
        try { return rs.getUnicodeStream(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public InputStream getBinaryStream(final String columnLabel)
    {
        try { return rs.getBinaryStream(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public SQLWarning getWarnings()
    {
        try { return rs.getWarnings(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void clearWarnings()
    {
        try { rs.clearWarnings(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public String getCursorName()
    {
        try { return rs.getCursorName(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public ResultSetMetaData getMetaData()
    {
        try { return rs.getMetaData(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Object getObject(final int columnIndex)
    {
        try { return rs.getObject(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Object getObject(final String columnLabel)
    {
        try { return rs.getObject(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int findColumn(final String columnLabel)
    {
        try { return rs.findColumn(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Reader getCharacterStream(final int columnIndex)
    {
        try { return rs.getCharacterStream(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Reader getCharacterStream(final String columnLabel)
    {
        try { return rs.getCharacterStream(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public BigDecimal getBigDecimal(final int columnIndex)
    {
        try { return rs.getBigDecimal(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public BigDecimal getBigDecimal(final String columnLabel)
    {
        try { return rs.getBigDecimal(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean isBeforeFirst()
    {
        try { return rs.isBeforeFirst(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean isAfterLast()
    {
        try { return rs.isAfterLast(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean isFirst()
    {
        try { return rs.isFirst(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean isLast()
    {
        try { return rs.isLast(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void beforeFirst()
    {
        try { rs.beforeFirst(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void afterLast()
    {
        try { rs.afterLast(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean first()
    {
        try { return rs.first(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean last()
    {
        try { return rs.last(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int getRow()
    {
        try { return rs.getRow(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean absolute(final int row)
    {
        try { return rs.absolute(row); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean relative(final int rows)
    {
        try { return rs.relative(rows); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean previous()
    {
        try { return rs.previous(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void setFetchDirection(final int direction)
    {
        try { rs.setFetchDirection(direction); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int getFetchDirection()
    {
        try { return rs.getFetchDirection(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void setFetchSize(final int rows)
    {
        try { rs.setFetchSize(rows); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int getFetchSize()
    {
        try { return rs.getFetchSize(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int getType()
    {
        try { return rs.getType(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int getConcurrency()
    {
        try { return rs.getConcurrency(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean rowUpdated()
    {
        try { return rs.rowUpdated(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean rowInserted()
    {
        try { return rs.rowInserted(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean rowDeleted()
    {
        try { return rs.rowDeleted(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNull(final int columnIndex)
    {
        try { rs.updateNull(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBoolean(final int columnIndex, final boolean x)
    {
        try { rs.updateBoolean(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateByte(final int columnIndex, final byte x)
    {
        try { rs.updateByte(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateShort(final int columnIndex, final short x)
    {
        try { rs.updateShort(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateInt(final int columnIndex, final int x)
    {
        try { rs.updateInt(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateLong(final int columnIndex, final long x)
    {
        try { rs.updateLong(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateFloat(final int columnIndex, final float x)
    {
        try { rs.updateFloat(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateDouble(final int columnIndex, final double x)
    {
        try { rs.updateDouble(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBigDecimal(final int columnIndex, final BigDecimal x)
    {
        try { rs.updateBigDecimal(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateString(final int columnIndex, final String x)
    {
        try { rs.updateString(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBytes(final int columnIndex, final byte[] x)
    {
        try { rs.updateBytes(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateDate(final int columnIndex, final Date x)
    {
        try { rs.updateDate(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateTime(final int columnIndex, final Time x)
    {
        try { rs.updateTime(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateTimestamp(final int columnIndex, final Timestamp x)
    {
        try { rs.updateTimestamp(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x, final int length)
    {
        try { rs.updateAsciiStream(columnIndex, x, length); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x, final int length)
    {
        try { rs.updateBinaryStream(columnIndex, x, length); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x, final int length)
    {
        try { rs.updateCharacterStream(columnIndex, x, length); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateObject(final int columnIndex, final Object x, final int scaleOrLength)
    {
        try { rs.updateObject(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateObject(final int columnIndex, final Object x)
    {
        try { rs.updateObject(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNull(final String columnLabel)
    {
        try { rs.updateNull(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBoolean(final String columnLabel, final boolean x)
    {
        try { rs.updateBoolean(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateByte(final String columnLabel, final byte x)
    {
        try { rs.updateByte(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateShort(final String columnLabel, final short x)
    {
        try { rs.updateShort(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateInt(final String columnLabel, final int x)
    {
        try { rs.updateInt(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateLong(final String columnLabel, final long x)
    {
        try { rs.updateLong(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateFloat(final String columnLabel, final float x)
    {
        try { rs.updateFloat(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateDouble(final String columnLabel, final double x)
    {
        try { rs.updateDouble(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBigDecimal(final String columnLabel, final BigDecimal x)
    {
        try { rs.updateBigDecimal(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateString(final String columnLabel, final String x)
    {
        try { rs.updateString(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBytes(final String columnLabel, final byte[] x)
    {
        try { rs.updateBytes(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateDate(final String columnLabel, final Date x)
    {
        try { rs.updateDate(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateTime(final String columnLabel, final Time x)
    {
        try { rs.updateTime(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateTimestamp(final String columnLabel, final Timestamp x)
    {
        try { rs.updateTimestamp(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x, final int length)
    {
        try { rs.updateAsciiStream(columnLabel, x, length); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x, final int length)
    {
        try { rs.updateBinaryStream(columnLabel, x, length); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader, final int length)
    {
        try { rs.updateCharacterStream(columnLabel, reader, length); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateObject(final String columnLabel, final Object x, final int scaleOrLength)
    {
        try { rs.updateObject(columnLabel, x, scaleOrLength); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateObject(final String columnLabel, final Object x)
    {
        try { rs.updateObject(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void insertRow()
    {
        try { rs.insertRow(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateRow()
    {
        try { rs.updateRow(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void deleteRow()
    {
        try { rs.deleteRow(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void refreshRow()
    {
        try { rs.refreshRow(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void cancelRowUpdates()
    {
        try { rs.cancelRowUpdates(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void moveToInsertRow()
    {
        try { rs.moveToInsertRow(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void moveToCurrentRow()
    {
        try { rs.moveToCurrentRow(); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Statement getStatement()
    {
        try { return rs.getStatement(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Object getObject(final int columnIndex, final Map<String, Class<?>> map)
    {
        try { return rs.getObject(columnIndex, map); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Ref getRef(final int columnIndex)
    {
        try { return rs.getRef(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Blob getBlob(final int columnIndex)
    {
        try { return rs.getBlob(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Clob getClob(final int columnIndex)
    {
        try { return rs.getClob(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Array getArray(final int columnIndex)
    {
        try { return rs.getArray(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Object getObject(final String columnLabel, final Map<String, Class<?>> map)
    {
        try { return rs.getObject(columnLabel, map); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Ref getRef(final String columnLabel)
    {
        try { return rs.getRef(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Blob getBlob(final String columnLabel)
    {
        try { return rs.getBlob(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Clob getClob(final String columnLabel)
    {
        try { return rs.getClob(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Array getArray(final String columnLabel)
    {
        try { return rs.getArray(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Date getDate(final int columnIndex, final Calendar cal)
    {
        try { return rs.getDate(columnIndex, cal); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Date getDate(final String columnLabel, final Calendar cal)
    {
        try { return rs.getDate(columnLabel, cal); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Time getTime(final int columnIndex, final Calendar cal)
    {
        try { return rs.getTime(columnIndex, cal); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Time getTime(final String columnLabel, final Calendar cal)
    {
        try { return rs.getTime(columnLabel, cal); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Timestamp getTimestamp(final int columnIndex, final Calendar cal)
    {
        try { return rs.getTimestamp(columnIndex, cal); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Timestamp getTimestamp(final String columnLabel, final Calendar cal)
    {
        try { return rs.getTimestamp(columnLabel, cal); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public URL getURL(final int columnIndex)
    {
        try { return rs.getURL(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public URL getURL(final String columnLabel)
    {
        try { return rs.getURL(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateRef(final int columnIndex, final Ref x)
    {
        try { rs.updateRef(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateRef(final String columnLabel, final Ref x)
    {
        try { rs.updateRef(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBlob(final int columnIndex, final Blob x)
    {
        try { rs.updateBlob(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBlob(final String columnLabel, final Blob x)
    {
        try { rs.updateBlob(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateClob(final int columnIndex, final Clob x)
    {
        try { rs.updateClob(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateClob(final String columnLabel, final Clob x)
    {
        try { rs.updateClob(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateArray(final int columnIndex, final Array x)
    {
        try { rs.updateArray(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateArray(final String columnLabel, final Array x)
    {
        try { rs.updateArray(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public RowId getRowId(final int columnIndex)
    {
        try { return rs.getRowId(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public RowId getRowId(final String columnLabel)
    {
        try { return rs.getRowId(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateRowId(final int columnIndex, final RowId x)
    {
        try { rs.updateRowId(columnIndex, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateRowId(final String columnLabel, final RowId x)
    {
        try { rs.updateRowId(columnLabel, x); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public int getHoldability()
    {
        try { return rs.getHoldability(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean isClosed()
    {
        try { return rs.isClosed(); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNString(final int columnIndex, final String nString)
    {
        try { rs.updateNString(columnIndex, nString); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNString(final String columnLabel, final String nString)
    {
        try { rs.updateNString(columnLabel, nString); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNClob(final int columnIndex, final NClob nClob)
    {
        try { rs.updateNClob(columnIndex, nClob); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNClob(final String columnLabel, final NClob nClob)
    {
        try { rs.updateNClob(columnLabel, nClob); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public NClob getNClob(final int columnIndex)
    {
        try { return rs.getNClob(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public NClob getNClob(final String columnLabel)
    {
        try { return rs.getNClob(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public SQLXML getSQLXML(final int columnIndex)
    {
        try { return rs.getSQLXML(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public SQLXML getSQLXML(final String columnLabel)
    {
        try { return rs.getSQLXML(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateSQLXML(final int columnIndex, final SQLXML xmlObject)
    {
        try { rs.updateSQLXML(columnIndex, xmlObject); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateSQLXML(final String columnLabel, final SQLXML xmlObject)
    {
        try { rs.updateSQLXML(columnLabel, xmlObject); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public String getNString(final int columnIndex)
    {
        try { return rs.getNString(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public String getNString(final String columnLabel)
    {
        try { return rs.getNString(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Reader getNCharacterStream(final int columnIndex)
    {
        try { return rs.getNCharacterStream(columnIndex); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public Reader getNCharacterStream(final String columnLabel)
    {
        try { return rs.getNCharacterStream(columnLabel); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x, final long length)
    {
        try { rs.updateNCharacterStream(columnIndex, x); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNCharacterStream(final String columnLabel, final Reader reader, final long length)
    {
        try { rs.updateNCharacterStream(columnLabel, reader, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x, final long length)
    {
        try { rs.updateAsciiStream(columnIndex, x, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x, final long length)
    {
        try { rs.updateBinaryStream(columnIndex, x, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x, final long length)
    {
        try { rs.updateCharacterStream(columnIndex, x, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x, final long length)
    {
        try { rs.updateAsciiStream(columnLabel, x, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x, final long length)
    {
        try { rs.updateBinaryStream(columnLabel, x); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader, final long length)
    {
        try { rs.updateCharacterStream(columnLabel, reader, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBlob(final int columnIndex, final InputStream inputStream, final long length)
    {
        try { rs.updateBlob(columnIndex, inputStream, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBlob(final String columnLabel, final InputStream inputStream, final long length)
    {
        try { rs.updateBlob(columnLabel, inputStream, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateClob(final int columnIndex, final Reader reader, final long length)
    {
        try { rs.updateClob(columnIndex, reader, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateClob(final String columnLabel, final Reader reader, final long length)
    {
        try { rs.updateClob(columnLabel, reader, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNClob(final int columnIndex, final Reader reader, final long length)
    {
        try { rs.updateNClob(columnIndex, reader, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNClob(final String columnLabel, final Reader reader, final long length)
    {
        try { rs.updateNClob(columnLabel, reader, length); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x)
    {
        try { rs.updateNCharacterStream(columnIndex, x); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNCharacterStream(final String columnLabel, final Reader reader)
    {
        try { rs.updateNCharacterStream(columnLabel, reader); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x)
    {
        try { rs.updateAsciiStream(columnIndex, x); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x)
    {
        try { rs.updateBinaryStream(columnIndex, x); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x)
    {
        try { rs.updateCharacterStream(columnIndex, x); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x)
    {
        try { rs.updateAsciiStream(columnLabel, x); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x)
    {
        try { rs.updateBinaryStream(columnLabel, x); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader)
    {
        try { rs.updateCharacterStream(columnLabel, reader); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBlob(final int columnIndex, final InputStream inputStream)
    {
        try { rs.updateBlob(columnIndex, inputStream); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateBlob(final String columnLabel, final InputStream inputStream)
    {
        try { rs.updateBlob(columnLabel, inputStream); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateClob(final int columnIndex, final Reader reader)
    {
        try { rs.updateClob(columnIndex, reader); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateClob(final String columnLabel, final Reader reader)
    {
        try { rs.updateClob(columnLabel, reader); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNClob(final int columnIndex, final Reader reader)
    {
        try { rs.updateNClob(columnIndex, reader); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public void updateNClob(final String columnLabel, final Reader reader)
    {
        try { rs.updateNClob(columnLabel, reader); } catch (SQLException e ) { throw new UncheckedSQLException(e); }
    }

    @Override
    public <T> T getObject(final int columnIndex, final Class<T> type)
    {
        try { return rs.getObject(columnIndex, type); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public <T> T getObject(final String columnLabel, final Class<T> type)
    {
        try { return rs.getObject(columnLabel, type); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public <T> T unwrap(final Class<T> iface)
    {
        try { return rs.unwrap(iface); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface)
    {
        try { return rs.isWrapperFor(iface); } catch (SQLException e) { throw new UncheckedSQLException(e); }
    }
}
