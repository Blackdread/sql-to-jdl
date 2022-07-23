package org.blackdread.sqltojava.shared.interfaces;

import org.slf4j.Logger;

public interface LoggingTest extends JdbcContainerTest {

    Logger log();
    void logger(Logger log);
}
