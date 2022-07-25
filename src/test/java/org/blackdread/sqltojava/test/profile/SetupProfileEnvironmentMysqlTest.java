package org.blackdread.sqltojava.test.profile;

import org.blackdread.sqltojava.extension.MySqlLatestExtension;
import org.blackdread.sqltojava.shared.interfaces.ProfileActiveTest;
import org.blackdread.sqltojava.shared.tests.BaseJdbcContainerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@ExtendWith(MySqlLatestExtension.class)
public class SetupProfileEnvironmentMysqlTest extends BaseJdbcContainerTest implements ProfileActiveTest {

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
}
