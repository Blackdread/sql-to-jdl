package org.blackdread.sqltojava.shared.interfaces;

import org.testcontainers.containers.JdbcDatabaseContainer;

public interface JdbcContainerTest {
    JdbcDatabaseContainer container();
    void container(JdbcDatabaseContainer container);
}
