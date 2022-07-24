package org.blackdread.sqltojava.shared.interfaces;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public interface ProfileActiveTest extends EnvironmentTest {
    @Test
    default void testCorrectProfileLoaded() {
        String expectedProfile = env().getProperty("expected.profile");
        assertNotNull("expected.profile should not be null.", expectedProfile);

        String[] activeProfiles = env().getActiveProfiles();
        String[] expectedProfiles = new String[] { expectedProfile };
        Assertions.assertArrayEquals(
            expectedProfiles,
            activeProfiles,
            "Incorrect profile set"
        );
    }
}
