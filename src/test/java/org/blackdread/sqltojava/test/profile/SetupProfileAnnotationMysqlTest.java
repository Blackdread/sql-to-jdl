package org.blackdread.sqltojava.test.profile;

import org.blackdread.sqltojava.extension.MySqlLatestExtension;
import org.blackdread.sqltojava.shared.interfaces.ProfileActiveTest;
import org.blackdread.sqltojava.shared.tests.BaseJdbcContainerTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@ExtendWith(MySqlLatestExtension.class)
@ActiveProfiles("mysql57")
public class SetupProfileAnnotationMysqlTest
    extends BaseJdbcContainerTest
    implements ProfileActiveTest {

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.enabled", () -> "false");
        registry.add("expected.profile", () -> "mysql57");
    }

}
