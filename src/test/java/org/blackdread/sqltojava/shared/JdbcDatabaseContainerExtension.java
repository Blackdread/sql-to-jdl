package org.blackdread.sqltojava.shared;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.JdbcDatabaseContainer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This is base class for JdbcDatabaseContainer set up for tests.
 */
public abstract class JdbcDatabaseContainerExtension implements BeforeAllCallback, AfterAllCallback {

    private JdbcDatabaseContainer container;
    protected abstract JdbcDatabaseContainer createContainer();

    /**
     * This method creates, starts, and configures the JdbcDatabaseContainer.
     * @param context
     */
    @Override
    public void beforeAll(ExtensionContext context) {
        container = createContainer();
        container.start();
        assertTrue(container.isRunning(),
                   String.format("Container %s should be running", container.getDockerImageName()));
        setupJdbcDatabaseContainer(container);
    }

    /**
     * This method stop the JdbcDatabaseContainer.
     * @param context
     */
    @Override
    public void afterAll(ExtensionContext context) {
        container.stop();
        assertFalse(container.isRunning(),
                    String.format("Container %s should not be running", container.getDockerImageName()));
    }

    /**
     * Set up default environment for JdbcDatabaseContainer.  The container must be running prior to being
     * passed to this method.
     * @param jdbcDatabaseContainer
     */
    public void setupJdbcDatabaseContainer(JdbcDatabaseContainer jdbcDatabaseContainer) {
        System.setProperty("spring.datasource.url", jdbcDatabaseContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", jdbcDatabaseContainer.getUsername());
        System.setProperty("spring.datasource.password", jdbcDatabaseContainer.getPassword());
    }

}
