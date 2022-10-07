package org.blackdread.sqltojava.test.db.mysql;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

class MysqlLatestTest extends SqlToJdlTransactionPerTestTest {

    @Container
    private static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql:latest"));

    @BeforeAll
    public static void setup() {
        System.setProperty("expected.profile", "mysql");
        setupContainer(MYSQL_CONTAINER);
    }
}
