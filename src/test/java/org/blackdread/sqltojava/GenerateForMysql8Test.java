package org.blackdread.sqltojava;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>Created on 2020/12/27.</p>
 *
 * @author Yoann CAPLAIN
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GenerateForMysql8Test {

    private static final Logger log = LoggerFactory.getLogger(GenerateForMysql8Test.class);

    @Container
//    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql").withTag("8.0"));
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql").withTag("8.0.22"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);

        registry.add("spring.flyway.locations", () -> "classpath:db/mysql8");

        registry.add("application.export.path", () -> "./mysql8.jdl");
    }

    @Test
    void containerRunning() throws IOException, URISyntaxException {
        assertTrue(MY_SQL_CONTAINER.isRunning());

        final List<String> expected = FileUtil.readAllLinesClasspath("/result/mysql8-expected.jdl");

        final List<String> actual = FileUtil.readAllLines("./mysql8.jdl");

        AssertUtil.assertFileSame(expected, actual);
    }

}
