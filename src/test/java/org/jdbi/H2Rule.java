package org.jdbi;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.rules.ExternalResource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class H2Rule extends ExternalResource implements DataSource
{
    private final List<String> fixtures;
    private JdbcConnectionPool ds;

    public H2Rule(List<String> fixtures) {
        this.fixtures = fixtures;
    }

    public H2Rule() {
        this(Collections.emptyList());
    }

    @Override
    protected void before() throws Throwable
    {
        ds = JdbcConnectionPool.create("jdbc:h2:mem:" + UUID.randomUUID(), "", "");
        try (Connection c = ds.getConnection())
        {
            for (String fixture : fixtures) {
                try (PreparedStatement ps = c.prepareStatement(fixture))
                {
                    ps.execute();
                }
            }
        }
    }

    @Override
    protected void after()
    {
        ds.dispose();
    }

    public JdbcConnectionPool getDatasource()
    {
        return ds;
    }

    public H2Rule withFixture(final String s)
    {
        List<String> fixes = new ArrayList<>(fixtures.size() + 1);
        fixes.addAll(this.fixtures);
        fixes.add(s);
        return new H2Rule(fixes);
    }

    @Override
    public Connection getConnection() throws SQLException
    {
        return ds.getConnection();
    }

    @Override
    public Connection getConnection(final String username, final String password) throws SQLException
    {
        return ds.getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException
    {
        return ds.getLogWriter();
    }

    @Override
    public void setLogWriter(final PrintWriter out) throws SQLException
    {
        ds.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(final int seconds) throws SQLException
    {
        ds.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException
    {
        return ds.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException
    {
        throw new UnsupportedOperationException("Not Yet Implemented!");
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException
    {
        return ds.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException
    {
        return ds.isWrapperFor(iface);
    }
}
