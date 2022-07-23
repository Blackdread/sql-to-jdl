package org.blackdread.sqltojava;

import org.blackdread.sqltojava.extension.MySqlLatestExtension;
import org.blackdread.sqltojava.shared.interfaces.CompareJdlResultsTest;
import org.blackdread.sqltojava.shared.tests.BaseJdbcContainerTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * <p>Created on 2020/12/27.</p>
 *
 * @author Yoann CAPLAIN
 */
@ExtendWith(MySqlLatestExtension.class)
public class GenerateForMysql8Test extends BaseJdbcContainerTest
    implements CompareJdlResultsTest {

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.locations", () -> "classpath:db/mysql8");
        registry.add("application.export.path", () -> "./mysql8.jdl");
        registry.add("expected.result.path", () -> "/result/mysql8-expected.jdl");
    }

}
