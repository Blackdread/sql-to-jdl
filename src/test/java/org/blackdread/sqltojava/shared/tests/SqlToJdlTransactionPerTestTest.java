package org.blackdread.sqltojava.shared.tests;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;
import liquibase.exception.LiquibaseException;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.service.logic.ExportService;
import org.blackdread.sqltojava.service.logic.JdlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

//@ExtendWith(JdbcDatabaseContainerExtension.class)
public abstract class SqlToJdlTransactionPerTestTest extends TransactionPerTestTest {
    @Autowired
    private JdlService jdlService;

    @Autowired
    private ExportService exportService;

    /**
     * To run a different set, implement this method in subclasses.
     * @return list of test names to run
     */
    private static Stream<String> provideTestNames() {
        return Stream.of(
            "all_types",
            "one_to_one",
            "many_to_one",
            "one_to_one_main_map",
            "parent_child",
            "unique",
            "enum",
            "views",
            "reflexive_relationship",
            "prune"
        );
    }

    /**
     * Clear all caches so each test will generate the correct JDL
     */
    @BeforeEach
    public void checkSchemaEmpty() {
        evictAllCaches();
    }

    /**
     * This method will run a liquibase changeset, generate jdl, compare it to the expected results,
     * and then rollback to an empty database before running the next test.
     * @param testName
     * @throws SQLException
     * @throws LiquibaseException
     */
    @ParameterizedTest
    @MethodSource("provideTestNames")
    public void testChangelog(String testName) throws SQLException, LiquibaseException {
        String pathJdlExpected = String.format("/jdl/%s-expected.jdl", testName);
        String jdlExpected = pathToString(pathJdlExpected);
        assertThat(jdlExpected).isNotNull().withFailMessage(String.format("%s was null", pathJdlExpected));

        String pathChangeLogFile = String.format("/jdl/%s-liquibase-changeset.yaml", testName);
        runChangeLogFile(pathChangeLogFile);

        List<JdlEntity> entities = jdlService.buildEntities();
        String jdlActual = exportService.exportString(entities);
        assertThat(jdlActual).isEqualTo(jdlExpected).withFailMessage("No Student");
    }
}
