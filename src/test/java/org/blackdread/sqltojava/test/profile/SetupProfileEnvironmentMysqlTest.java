package org.blackdread.sqltojava.test.profile;

import org.blackdread.sqltojava.extension.MySqlLatestExtension;
import org.blackdread.sqltojava.shared.MainApplicationContextLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(loader = MainApplicationContextLoader.class)
@ExtendWith(MySqlLatestExtension.class)
public class SetupProfileEnvironmentMysqlTest {

    @Autowired
    private Environment env;

    /**
     * spring.profiles.active must be set in the @BeforeAll phase.
     */
    @BeforeAll
    static void beforeAll() {
      System.setProperty("spring.profiles.active", "mysql57");
    }

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
        assertArrayEquals(expectedProfiles, activeProfiles, "Incorrect profile set");
    }

}
