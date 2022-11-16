package org.blackdread.sqltojava.test.general;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.blackdread.sqltojava.util.JdlUtils;
import org.junit.jupiter.api.Test;

public class DatabaseObjectPrefixTest {

    @Test
    public void testPrefix() {
        String name = JdlUtils.getEntityName("t_test", List.of("t_"));
        assertThat(name).isEqualTo("Test");
    }
}
