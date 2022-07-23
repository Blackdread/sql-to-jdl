package org.blackdread.sqltojava.shared.tests;

import org.blackdread.sqltojava.shared.LoggingExtension;
import org.blackdread.sqltojava.shared.MainApplicationContextLoader;
import org.blackdread.sqltojava.shared.interfaces.ContainersStartedTest;
import org.blackdread.sqltojava.shared.interfaces.EnvironmentTest;
import org.blackdread.sqltojava.shared.interfaces.JdbcContainerTest;
import org.blackdread.sqltojava.shared.interfaces.LoggingTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(LoggingExtension.class)
@ContextConfiguration(loader = MainApplicationContextLoader.class)
public abstract class BaseJdbcContainerTest
    implements
        LoggingTest, EnvironmentTest, JdbcContainerTest, ContainersStartedTest {
    private static Logger log;
    private JdbcDatabaseContainer container;

    // EnvironmentTest
    @Autowired
    private Environment env;

    @Override
    public Environment env() {
        return env;
    }

    // LoggingTest
    @Override
    public Logger log() {
        return log;
    }

    @Override
    public void logger(Logger log) {
        BaseJdbcContainerTest.log = log;
    }

    // JdbcContainerTest
    @Override
    public JdbcDatabaseContainer container() {
        return container;
    }

    @Override
    public void container(JdbcDatabaseContainer container) {
        this.container = container;
    }
}
