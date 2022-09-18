package org.blackdread.sqltojava;

import org.blackdread.sqltojava.extension.MsSqlLatestExtension;
import org.blackdread.sqltojava.shared.MainApplicationContextLoader;
import org.blackdread.sqltojava.shared.interfaces.CompareJdlResultsTest;
import org.blackdread.sqltojava.shared.tests.BaseJdbcContainerTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * <p>Created on 2021/01/02.</p>
 *
 * @author Yoann CAPLAIN
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(loader = MainApplicationContextLoader.class)
@ExtendWith(MsSqlLatestExtension.class)
@Disabled
public class GenerateForSqlServerTest extends BaseJdbcContainerTest implements CompareJdlResultsTest {

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.locations", () -> "classpath:db/mssql");
        registry.add("application.export.path", () -> "./mssql.jdl");
        registry.add("expected.result.path", () -> "/result/mssql-expected.jdl");
        registry.add("spring.liquibase.enabled", () -> false);
    }
}
