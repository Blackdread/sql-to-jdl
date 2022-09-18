package org.blackdread.sqltojava.db.mssql;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@Disabled
class MssqlSql2019Test extends SqlToJdlTransactionPerTestTest {
    @Container
    private static final MSSQLServerContainer MSSQL_CONTAINER = new MSSQLServerContainer(
        DockerImageName.parse("mcr.microsoft.com/mssql/server").withTag("2019-latest")
    )
    .acceptLicense();

    @BeforeAll
    public static void setup() {
        setupContainer(MSSQL_CONTAINER);
    }
}
