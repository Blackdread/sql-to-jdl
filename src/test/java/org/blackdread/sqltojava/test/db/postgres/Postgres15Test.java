package org.blackdread.sqltojava.test.db.postgres;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

class Postgres15Test extends SqlToJdlTransactionPerTestTest {
    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
        DockerImageName.parse("postgres:15beta4-alpine")
    );

    @BeforeAll
    public static void setup() {
        System.setProperty("expected.profile", "postgresql");
        setupContainer(POSTGRE_SQL_CONTAINER);
    }
}
