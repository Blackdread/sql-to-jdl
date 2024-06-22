package org.blackdread.sqltojava.service.logic;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.config.ExportFileStructureType;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.exporter.ExportFileStructureConfig;
import org.blackdread.sqltojava.util.JdlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

    private static final Logger log = LoggerFactory.getLogger(ExportService.class);

    private final ApplicationProperties properties;
    private final ExportFileStructureConfig exportFileStructureConfig;

    private final JdlService jdlService;

    private final MustacheService mustacheService;

    public ExportService(
        final ApplicationProperties properties,
        ExportFileStructureConfig exportFileStructureConfig,
        JdlService jdlService,
        MustacheService mustacheService
    ) {
        this.properties = properties;
        this.exportFileStructureConfig = exportFileStructureConfig;
        this.jdlService = jdlService;
        this.mustacheService = mustacheService;
    }

    /**
     * Exports jdl to string using mustache.java
     * @param entities
     * @return
     */
    public String exportString(final List<JdlEntity> entities) {
        return mustacheService.executeTemplate(
            exportFileStructureConfig.getExportMustacheTemplateFilename(),
            resolveExportStructure(entities)
        );
    }

    public Map<String, Object> resolveExportStructure(final List<JdlEntity> entities) {
        if (ExportFileStructureType.SEPARATED.equals(exportFileStructureConfig.getExportFileStructureType())) {
            return ofEntries(
                entry("entities", entities),
                entry("relations", jdlService.getRelations(entities)),
                entry("options", !properties.isRenderEntitiesOnly() ? JdlUtils.getOptions() : Collections.emptyList())
            );
        } else if (
            ExportFileStructureType.GROUPED_RELATIONS_SEPARATE_VIEWS.equals(exportFileStructureConfig.getExportFileStructureType())
        ) {
            List<JdlEntity> tables = extractTables(entities);
            List<JdlEntity> views = extractViews(entities);

            return ofEntries(
                entry("entities", tables),
                entry("groupedonetoonerelations", jdlService.getGroupOneToOneRelations(tables)),
                entry("groupedmanytoonerelations", jdlService.getGroupManyToOneRelations(tables)),
                entry("manytomanyrelations", jdlService.getManyToManyRelations(tables)),
                entry("views", views),
                entry("viewrelations", jdlService.getRelations(views)),
                entry("options", !properties.isRenderEntitiesOnly() ? JdlUtils.getOptions() : Collections.emptyList())
            );
        } else if (
            ExportFileStructureType.RELATIONS_BEFORE_VIEWS_SEPARATE_VIEWS.equals(exportFileStructureConfig.getExportFileStructureType())
        ) {
            List<JdlEntity> tables = extractTables(entities);
            List<JdlEntity> views = extractViews(entities);

            return ofEntries(
                entry("entities", tables),
                entry("onetoonerelations", jdlService.getOneToOneRelations(tables)),
                entry("manytoonerelations", jdlService.getManyToOneRelations(tables)),
                entry("manytomanyrelations", jdlService.getManyToManyRelations(tables)),
                entry("views", views),
                entry("viewrelations", jdlService.getRelations(views)),
                entry("options", !properties.isRenderEntitiesOnly() ? JdlUtils.getOptions() : Collections.emptyList())
            );
        }
        throw new IllegalStateException("Unknown export file structure type");
    }

    private static List<JdlEntity> extractViews(List<JdlEntity> entities) {
        return entities.stream().filter(JdlEntity::isReadOnly).toList();
    }

    private static List<JdlEntity> extractTables(List<JdlEntity> entities) {
        return entities.stream().filter(e -> !e.isReadOnly()).toList();
    }

    /**
     * Exports jdl to file defined with export.path
     * @param entities
     * @return
     */
    public void export(final List<JdlEntity> entities) {
        final String jdl = exportString(entities);
        final Path path = properties.getExport().getPath();

        log.info("Exporting into: {}", path.toAbsolutePath());

        if (Files.isDirectory(path)) {
            log.error("Path is a directory: {}", path.toAbsolutePath());
            throw new IllegalArgumentException("Cannot export into a directory");
        }

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("An error occurred when deleting export file");
            throw new IllegalStateException(e);
        }

        try (BufferedWriter out = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            out.write(jdl);
            out.flush();
        } catch (IOException e) {
            log.error("Error", e);
            throw new IllegalStateException(e);
        }
    }
}
