package org.blackdread.sqltojava.test.db.postgres;

import java.util.stream.Stream;
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
class PostgresLatestTest extends SqlToJdlTransactionPerTestTest {

    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));

    @SystemStub
    private static EnvironmentVariables env;

    @BeforeAll
    public static void setup() {
        env.set("expected.profile", "postgresql");
        setupContainer(POSTGRE_SQL_CONTAINER);
    }

    private static Stream<String> provideTestNames_() {
        return Stream.of("one_to_one");
    }
}
