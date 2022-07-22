package org.blackdread.sqltojava.shared.interfaces;

import org.blackdread.sqltojava.AssertUtil;
import org.blackdread.sqltojava.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface CompareJdlResultsTest
    extends LoggingTest,
            EnvironmentTest {

    @Test
    default void testFileSame() throws URISyntaxException, IOException {
        String expectedResultPath = env().getProperty("expected.result.path");
        String applicationExportPath = env().getProperty("application.export.path");
        log().info("expected.result.path: " + expectedResultPath);
        log().info("application.export.path: " + applicationExportPath);

        final List<String> expected = FileUtil.readAllLinesClasspath(expectedResultPath);
        final List<String> actual = FileUtil.readAllLines(applicationExportPath);
        AssertUtil.assertFileSame(expected, actual);
    }
}
