package org.blackdread.sqltojava.test.db.mysql;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

//@Disabled("Fails enum test")
class MariaDBLatestTest extends SqlToJdlTransactionPerTestTest {
    @Container
    private static final MariaDBContainer MARIA_DB_CONTAINER = new MariaDBContainer(DockerImageName.parse("mariadb:latest"));

    @BeforeAll
    public static void setup() {
        System.setProperty("expected.profile", "mariadb");
        setupContainer(MARIA_DB_CONTAINER);
    }
}
