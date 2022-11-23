package org.blackdread.sqltojava.test.db.mysql;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
class MariaDBLatestTest extends SqlToJdlTransactionPerTestTest {

    @Container
    private static final MariaDBContainer MARIA_DB_CONTAINER = new MariaDBContainer(DockerImageName.parse("mariadb:latest"));

    @SystemStub
    private static EnvironmentVariables env;

    @BeforeAll
    public static void setup() {
        env.set("expected.profile", "mariadb");
        setupContainer(MARIA_DB_CONTAINER);
    }
}
