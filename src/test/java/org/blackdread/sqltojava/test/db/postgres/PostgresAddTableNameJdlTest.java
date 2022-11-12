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
class PostgresAddTableNameJdlTest extends SqlToJdlTransactionPerTestTest {

    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));

    @SystemStub
    private static EnvironmentVariables environmentVariables;

    @BeforeAll
    public static void setup() {
        environmentVariables.set("expected.profile", "postgresql");
        environmentVariables.set("application.add_table_name_jdl", "true");
//        System.setProperty("expected.profile", "postgresql");
//        System.setProperty("application.add_table_name_jdl", "true");
        setupContainer(POSTGRE_SQL_CONTAINER);
    }

    private static Stream<String> provideTestNames() {
        return Stream.of("table_name");
    }
}
