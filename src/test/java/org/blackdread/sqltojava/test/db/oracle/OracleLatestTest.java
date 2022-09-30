package org.blackdread.sqltojava.test.db.oracle;

import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.OracleContainer;

@Disabled("Implementation incomplete")
class OracleLatestTest extends SqlToJdlTransactionPerTestTest {
    @Container
    private static final OracleContainer ORACLE_CONTAINER = new OracleContainer(DockerImageName.parse("gvenzl/oracle-xe:latest"));

    @BeforeAll
    public static void setup() {
        setupContainer(ORACLE_CONTAINER);
    }
}
