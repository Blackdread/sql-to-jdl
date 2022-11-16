package org.blackdread.sqltojava.shared.tests;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.SQLException;
import java.util.stream.Stream;
import liquibase.exception.LiquibaseException;
import org.blackdread.sqltojava.service.logic.ExportService;
import org.blackdread.sqltojava.service.logic.JdlService;
import org.blackdread.sqltojava.shared.interfaces.ProfileActiveTest;
import org.blackdread.sqltojava.util.ResourceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SqlToJdlTransactionPerTestTest extends TransactionPerTestTest implements ProfileActiveTest {

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
            "uuid_id_required",
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
        String activeProfile = env().getActiveProfiles()[0];
        String pathChangeLogFile = getExpectedLiquibaseChangeset(testName, activeProfile);
        runChangeLogFile(pathChangeLogFile);

        String jdlExpected = getExpectedJdl(testName, activeProfile);
        String jdlActual = exportService.exportString(jdlService.buildEntities());
        assertThat(jdlActual).isEqualTo(jdlExpected).withFailMessage("JDL output did not match expected.");
    }

    /**
     * The expected jdl file will first be searched for at /jdl/testName-liquibase-changeset-activeProfile.yaml and then at
     * /jdl/testName-liquibase-changeset.yaml.  If neither match an error will be thrown.
     *
     * @param testName
     * @param activeProfile
     * @return
     */
    private String getExpectedLiquibaseChangeset(String testName, String activeProfile) {
        String pathChangesetProfile = String.format("/jdl/%s-liquibase-changeset-%s.yaml", testName, activeProfile);
        String pathChangesetDefault = String.format("/jdl/%s-liquibase-changeset.yaml", testName);
        return ResourceUtil.getFirstExistingResourcePath("liquibase-changeset.yaml", pathChangesetProfile, pathChangesetDefault);
    }

    /**
     * The expected jdl file will first be searched for at /jdl/testName-expected-activeProfile.jdl and then at
     * /jdl/testName-expected.jdl.  If neither match an error will be thrown.
     *
     * @param testName
     * @param activeProfile
     * @return
     */
    private String getExpectedJdl(String testName, String activeProfile) {
        String pathJdlExpectedProfile = String.format("/jdl/%s-expected-%s.jdl", testName, activeProfile);
        String pathJdlExpectedDefault = String.format("/jdl/%s-expected.jdl", testName);
        String pathJdlExpected = ResourceUtil.getFirstExistingResourcePath("expected.jdl", pathJdlExpectedProfile, pathJdlExpectedDefault);
        return pathToString(pathJdlExpected);
    }
}
