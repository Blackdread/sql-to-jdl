package org.blackdread.sqltojava;

import org.blackdread.sqltojava.extension.MsSqlLatestExtension;
import org.blackdread.sqltojava.shared.MainApplicationContextLoader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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
public class GenerateForSqlServerTest {

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.locations", () -> "classpath:db/mssql");
        registry.add("application.export.path", () -> "./mssql.jdl");
    }

    @Test
    void containerRunning() throws IOException, URISyntaxException {

        final List<String> expected = FileUtil.readAllLinesClasspath("/result/mssql-expected.jdl");

        final List<String> actual = FileUtil.readAllLines("./mssql.jdl");

        AssertUtil.assertFileSame(expected, actual);
    }

}
