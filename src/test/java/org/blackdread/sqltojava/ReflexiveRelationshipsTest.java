package org.blackdread.sqltojava;

import org.blackdread.sqltojava.extension.MySqlLatestExtension;
import org.blackdread.sqltojava.shared.MainApplicationContextLoader;
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

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(loader = MainApplicationContextLoader.class)
@ExtendWith(MySqlLatestExtension.class)
public class ReflexiveRelationshipsTest {

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.locations", () -> "classpath:db/self-reference");
        registry.add("application.export.path", () -> "./reflexive.jdl");
    }

    @Test
    void containerRunning() throws IOException, URISyntaxException {

        final List<String> expected = FileUtil.readAllLinesClasspath("/result/reflexive-expected.jdl");

        final List<String> actual = FileUtil.readAllLines("./reflexive.jdl");

        AssertUtil.assertFileSame(expected, actual);
    }
}
