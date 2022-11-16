package org.blackdread.sqltojava.test.db.postgres;

import java.util.stream.Stream;
import org.blackdread.sqltojava.shared.tests.SqlToJdlTransactionPerTestTest;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

class PostgresDatabaseObjectPrefixTest extends SqlToJdlTransactionPerTestTest {

    @Container
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres:latest"));

    @BeforeAll
    public static void setup() {
        System.setProperty("expected.profile", "postgresql");
        //System.setProperty("database-object-prefix", "t_,v_");
        setupContainer(POSTGRE_SQL_CONTAINER);
    }

    private static Stream<String> provideTestNames() {
        return Stream.of("prefix");
    }
}
