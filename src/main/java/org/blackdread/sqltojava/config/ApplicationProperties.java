package org.blackdread.sqltojava.config;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.nio.file.Path;
import java.util.List;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.util.ResourceUtils;

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

    private final List<String> reservedList;

    @SuppressWarnings("unchecked")
    public ApplicationProperties(final String databaseToExport, final List<String> ignoredTableNames, final Export export, final String reservedKeywords) {
        this.databaseToExport = databaseToExport;
        this.ignoredTableNames = ignoredTableNames;
        this.export = export;
        this.reservedList = JsonParserFactory.getJsonParser()
            .parseMap(keywordsAsJson(reservedKeywords)).values()
            .stream()
            .map(obj -> (List<String>) obj)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
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

    public List<String> getReservedList() {
        return reservedList;
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

    private String keywordsAsJson(String file) {
        try {
            Path path = ResourceUtils.getFile(file).toPath();
            return String.join(" ", Files.readAllLines(path));
        } catch (IOException e) {
            return "{\"key\": []}";
        }
    }

}
