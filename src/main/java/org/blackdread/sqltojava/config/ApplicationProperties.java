package org.blackdread.sqltojava.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.List;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String tableToExport;

    private List<String> ignoredTableNames;

    private final Export export = new Export();


    public String getTableToExport() {
        return tableToExport;
    }

    public void setTableToExport(final String tableToExport) {
        this.tableToExport = tableToExport;
    }

    public List<String> getIgnoredTableNames() {
        return ignoredTableNames;
    }

    public void setIgnoredTableNames(final List<String> ignoredTableNames) {
        this.ignoredTableNames = ignoredTableNames;
    }

    public Export getExport() {
        return export;
    }

    public static class Export {

        private Path path;

        private String type;


        public Path getPath() {
            return path;
        }

        public void setPath(final Path path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(final String type) {
            this.type = type;
        }
    }
}
