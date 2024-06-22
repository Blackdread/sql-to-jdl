package org.blackdread.sqltojava.test.db.oracle;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
class OracleLatestTest extends SqlToJdlTransactionPerTestTest {

    @Container
    private static final OracleContainer ORACLE_CONTAINER = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:latest"));

    @SystemStub
    private static EnvironmentVariables env;

    @BeforeAll
    public static void setup() {
        env.set("expected.profile", "oracle");
        setupContainer(ORACLE_CONTAINER);
    }
}
