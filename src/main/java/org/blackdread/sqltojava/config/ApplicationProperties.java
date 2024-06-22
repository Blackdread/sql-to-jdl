package org.blackdread.sqltojava.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.util.ResourceUtils;

@ConstructorBinding
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private static final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);

    /**
     * Database name to export to JDL
     */
    private final String databaseToExport;
    private final List<String> databaseObjectPrefix;
    private final boolean addTableNameJdl;
    private final UndefinedJdlTypeHandlingEnum undefinedTypeHandling;
    private final DatabaseObjectTypesConfigEnum databaseObjectTypesConfig;
    private final Boolean renderEntitiesOnly;
    private final Boolean assumeBidirectional;
    private final List<String> ignoredTableNames;
    private final Export export;
    private final List<String> reservedList;
    private final Map<String, JdlFieldEnum> jdlTypeOverrides;

    @SuppressWarnings("unchecked")
    public ApplicationProperties(
        final String databaseToExport,
        final List<String> databaseObjectPrefix,
        final Boolean addTableNameJdl,
        final UndefinedJdlTypeHandlingEnum undefinedTypeHandling,
        final DatabaseObjectTypesConfigEnum databaseObjectTypesConfig,
        final Boolean renderEntitiesOnly,
        final Boolean assumeBidirectional,
        final List<String> ignoredTableNames,
        final Export export,
        final String reservedKeywords,
        final Map<String, JdlFieldEnum> jdlTypeOverrides
    ) {
        log.info("Loading ApplicationProperties...");
        this.databaseToExport = databaseToExport;
        this.databaseObjectPrefix = databaseObjectPrefix;
        this.undefinedTypeHandling = undefinedTypeHandling;
        this.databaseObjectTypesConfig = databaseObjectTypesConfig;
        this.renderEntitiesOnly = renderEntitiesOnly;
        this.assumeBidirectional = assumeBidirectional;
        this.addTableNameJdl = Optional.of(addTableNameJdl).orElse(false);
        this.ignoredTableNames = ignoredTableNames;
        this.export = export;
        this.reservedList =
            JsonParserFactory
                .getJsonParser()
                .parseMap(keywordsAsJson(reservedKeywords))
                .values()
                .stream()
                .map(obj -> (List<String>) obj)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        this.jdlTypeOverrides = Optional.ofNullable(jdlTypeOverrides).orElse(Collections.emptyMap());
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

    public Boolean getAddTableNameJdl() {
        return addTableNameJdl;
    }

    public UndefinedJdlTypeHandlingEnum getUndefinedTypeHandling() {
        return undefinedTypeHandling;
    }

    public DatabaseObjectTypesConfigEnum getDatabaseObjectTypesConfig() {
        return databaseObjectTypesConfig;
    }

    public Boolean isRenderEntitiesOnly() {
        return renderEntitiesOnly;
    }

    public Boolean isAssumeBidirectional() {
        return assumeBidirectional;
    }

    public List<String> getDatabaseObjectPrefix() {
        return databaseObjectPrefix;
    }

    public Map<String, JdlFieldEnum> getJdlTypeOverrides() {
        return jdlTypeOverrides;
    }

    public static class Export {

        private final Path path;

        private final String type;
        private final ExportFileStructureType exportFileStructureType;
        private final String exportMustacheTemplateFilenameOptional;

        public Export(
            final Path path,
            final String type,
            ExportFileStructureType exportFileStructureType,
            String exportMustacheTemplateFilenameOptional
        ) {
            this.path = path;
            this.type = type;
            this.exportFileStructureType = exportFileStructureType;
            this.exportMustacheTemplateFilenameOptional = exportMustacheTemplateFilenameOptional;
        }

        public Path getPath() {
            return path;
        }

        public String getType() {
            return type;
        }

        public ExportFileStructureType getExportFileStructureType() {
            return exportFileStructureType;
        }

        public String getExportMustacheTemplateFilenameOptional() {
            return exportMustacheTemplateFilenameOptional;
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
