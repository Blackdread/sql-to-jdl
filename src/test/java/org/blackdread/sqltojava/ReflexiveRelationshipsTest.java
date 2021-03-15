package org.blackdread.sqltojava;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ReflexiveRelationshipsTest {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(
        DockerImageName.parse("mysql").withTag("8.0.22"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);

        registry.add("spring.flyway.locations", () -> "classpath:db/self-reference");
        registry.add("application.export.path", () -> "./reflexive.jdl");
    }

    @Test
    void containerRunning() throws IOException, URISyntaxException {
        assertTrue(MY_SQL_CONTAINER.isRunning());

        final List<String> expected = FileUtil.readAllLinesClasspath("/result/reflexive-expected.jdl");

        final List<String> actual = FileUtil.readAllLines("./reflexive.jdl");

        AssertUtil.assertFileSame(expected, actual);
    }
}
