package org.blackdread.sqltojava.shared.interfaces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface ContainersStartedTest extends JdbcContainerTest {

    @Test
    default void testContainerRunning() {
        boolean test = container().isRunning();
        String message = String.format("Container %s is not running", container().getDockerImageName());
        assertTrue(test, message);
    }

}
