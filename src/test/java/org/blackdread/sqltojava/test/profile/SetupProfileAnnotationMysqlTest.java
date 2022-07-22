package org.blackdread.sqltojava.test.profile;

import org.blackdread.sqltojava.extension.MySqlLatestExtension;
import org.blackdread.sqltojava.shared.MainApplicationContextLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(loader = MainApplicationContextLoader.class)
@ExtendWith(MySqlLatestExtension.class)
@ActiveProfiles("mysql57")
public class SetupProfileAnnotationMysqlTest {

    @Autowired
    private Environment env;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.enabled", () -> "false");
        registry.add("expected.profile", () -> "mysql57");
    }

    @Test
    public void correctProfileLoaded() {
        String expectedProfile = env.getProperty("expected.profile");
        assertNotNull("expected.profile should not be null.", expectedProfile);

        String[] activeProfiles = env.getActiveProfiles();
        String[] expectedProfiles = new String[]{expectedProfile};
        Assertions.assertArrayEquals(expectedProfiles, activeProfiles, "Incorrect profile set");
    }

}
