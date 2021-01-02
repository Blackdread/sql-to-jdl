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
public class GenerateForMysql57Test {

    private static final Logger log = LoggerFactory.getLogger(GenerateForMysql57Test.class);

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql").withTag("5.7.22"));
//    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql").withTag("5.7.32"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);

        registry.add("spring.flyway.locations", () -> "classpath:db/mysql57");

        registry.add("application.export.path", () -> "./mysql57.jdl");
    }

    @Test
    void containerRunning() throws URISyntaxException, IOException {
        assertTrue(MY_SQL_CONTAINER.isRunning());

        final List<String> expected = FileUtil.readAllLinesClasspath("/result/mysql57-expected.jdl");

        final List<String> actual = FileUtil.readAllLines("./mysql57.jdl");

        AssertUtil.assertFileSame(expected, actual);
    }

}
