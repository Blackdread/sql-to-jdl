package org.blackdread.sqltojava.test.general.unsupported;

import java.util.stream.Stream;
import org.blackdread.sqltojava.config.UndefinedJdlTypeHandlingEnum;
import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
class PostgresUndefinedErrorTest extends SqlToJdlTransactionPerTestTest {

    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));

    @SystemStub
    private static EnvironmentVariables env;

    @BeforeAll
    public static void setup() {
        env.set("expected.profile", "postgresql");
        env.set("application.undefined_type_handling", UndefinedJdlTypeHandlingEnum.ERROR);
        setupContainer(POSTGRE_SQL_CONTAINER);
    }

    @ParameterizedTest
    @MethodSource("provideTestNames")
    public void testChangelog(String testName) {
        testJdlException(testName, RuntimeException.class);
    }

    private static Stream<String> provideTestNames() {
        return Stream.of("undefined_error");
    }
}
