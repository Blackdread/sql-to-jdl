package org.blackdread.sqltojava;

import org.blackdread.sqltojava.extension.MySqlLatestExtension;
import org.blackdread.sqltojava.shared.MainApplicationContextLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * <p>Created on 2020/12/27.</p>
 *
 * @author Yoann CAPLAIN
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(loader = MainApplicationContextLoader.class)
@ExtendWith(MySqlLatestExtension.class)
public class GenerateForMysql8Test {

    private static final Logger log = LoggerFactory.getLogger(GenerateForMysql8Test.class);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.locations", () -> "classpath:db/mysql8");
        registry.add("application.export.path", () -> "./mysql8.jdl");
    }

    @Test
    void containerRunning() throws IOException, URISyntaxException {

        final List<String> expected = FileUtil.readAllLinesClasspath("/result/mysql8-expected.jdl");

        final List<String> actual = FileUtil.readAllLines("./mysql8.jdl");

        AssertUtil.assertFileSame(expected, actual);
    }

}
