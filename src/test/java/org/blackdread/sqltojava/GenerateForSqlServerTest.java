package org.blackdread.sqltojava;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>Created on 2021/01/02.</p>
 *
 * @author Yoann CAPLAIN
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GenerateForSqlServerTest {

    private static final Logger log = LoggerFactory.getLogger(GenerateForSqlServerTest.class);

    @Container
    private static final MSSQLServerContainer MSSQL_CONTAINER = new MSSQLServerContainer(DockerImageName.parse("mcr.microsoft.com/mssql/server").withTag("2019-latest")).acceptLicense();

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MSSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", MSSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", MSSQL_CONTAINER::getUsername);

        registry.add("spring.flyway.locations", () -> "classpath:db/mssql");

        registry.add("application.export.path", () -> "./mssql.jdl");
    }

    @Test
    void containerRunning() throws IOException, URISyntaxException {
        assertTrue(MSSQL_CONTAINER.isRunning());

        final List<String> expected = FileUtil.readAllLinesClasspath("/result/mssql-expected.jdl");

        final List<String> actual = FileUtil.readAllLines("./mssql.jdl");

        AssertUtil.assertFileSame(expected, actual);
    }

}
