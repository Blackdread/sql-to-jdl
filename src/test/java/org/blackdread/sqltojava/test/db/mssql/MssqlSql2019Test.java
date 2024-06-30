package org.blackdread.sqltojava.test.db.mssql;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith(SystemStubsExtension.class)
class MssqlSql2019Test extends SqlToJdlTransactionPerTestTest {

    @Container
    private static final MSSQLServerContainer MSSQL_CONTAINER = new MSSQLServerContainer(
        DockerImageName.parse("mcr.microsoft.com/mssql/server").withTag("2019-latest")
    )
        .acceptLicense();

    @SystemStub
    private static EnvironmentVariables env;

    protected static void setEnv(String name, String value) {
        env.set(name, value);
    }

    @BeforeAll
    public static void setup() {
        setEnv("expected.profile", "sqlserver");
        setupContainer(MSSQL_CONTAINER);
    }
}
