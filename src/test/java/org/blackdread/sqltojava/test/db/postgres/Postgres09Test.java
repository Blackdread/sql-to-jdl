package org.blackdread.sqltojava.test.db.postgres;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
class Postgres09Test extends SqlToJdlTransactionPerTestTest {

    static String dockerImageName;

    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres:9"));

    @SystemStub
    private static EnvironmentVariables env;

    @BeforeAll
    public static void setup() {
        env.set("expected.profile", "postgresql");
        setupContainer(POSTGRE_SQL_CONTAINER);
    }
}
