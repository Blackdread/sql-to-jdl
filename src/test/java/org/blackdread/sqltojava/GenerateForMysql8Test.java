package org.blackdread.sqltojava;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>Created on 2020/12/27.</p>
 *
 * @author Yoann CAPLAIN
 */
@Testcontainers
public class GenerateForMysql8Test {

    private static final Logger log = LoggerFactory.getLogger(GenerateForMysql8Test.class);

    @Container
//    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql").withTag("8.0"));
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql").withTag("8.0.22"));

    @Test
    void containerRunning() {
        assertTrue(MY_SQL_CONTAINER.isRunning());
    }

}
