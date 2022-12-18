package org.blackdread.sqltojava.service.logic;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.util.JdlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

    private static final Logger log = LoggerFactory.getLogger(ExportService.class);

    private final ApplicationProperties applicationProperties;

    private final JdlService jdlService;

    private final MustacheService mustacheService;

    public ExportService(final ApplicationProperties applicationProperties, JdlService jdlService, MustacheService mustacheService) {
        this.applicationProperties = applicationProperties;
        this.jdlService = jdlService;
        this.mustacheService = mustacheService;
    }

    /**
     * Exports jdl to string using mustache.java
     * @param entities
     * @return
     */
    public String exportString(final List<JdlEntity> entities) {
        Map<String, Object> context = ofEntries(
            entry("entities", entities),
            entry("relations", jdlService.getRelations(entities)),
            entry("options", JdlUtils.getOptions())
        );
        return mustacheService.executeTemplate("application", context);
    }

    /**
     * Exports jdl to file defined with export.path
     * @param entities
     * @return
     */
    public void export(final List<JdlEntity> entities) {
        final String jdl = exportString(entities);
        final Path path = applicationProperties.getExport().getPath();

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
