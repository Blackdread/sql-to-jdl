package org.blackdread.sqltojava.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.nio.file.Path;
import java.util.List;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    /**
     * Database name to export to JDL
     */
    private final String databaseToExport;

    private final List<String> ignoredTableNames;

    private final Export export;

    public ApplicationProperties(final String databaseToExport, final List<String> ignoredTableNames, final Export export) {
        this.databaseToExport = databaseToExport;
        this.ignoredTableNames = ignoredTableNames;
        this.export = export;
    }

    public String getDatabaseToExport() {
        return databaseToExport;
    }

    public List<String> getIgnoredTableNames() {
        return ignoredTableNames;
    }

    public Export getExport() {
        return export;
    }

    public static class Export {

        private final Path path;

        private final String type;

        public Export(final Path path, final String type) {
            this.path = path;
            this.type = type;
        }

        public Path getPath() {
            return path;
        }

        public String getType() {
            return type;
        }

    }

}
