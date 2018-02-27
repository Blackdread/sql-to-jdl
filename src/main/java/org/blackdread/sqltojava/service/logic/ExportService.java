package org.blackdread.sqltojava.service.logic;

import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;
import org.blackdread.sqltojava.util.JdlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * <p>Created on 2018/2/9.</p>
 *
 * @author Yoann CAPLAIN
 */
@Service
public class ExportService {

    private static final Logger log = LoggerFactory.getLogger(ExportService.class);

    private final ApplicationProperties applicationProperties;

    public ExportService(final ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void export(final List<JdlEntity> entities) {

        // 1. Entities
        // 2. Relations
        // 3. Options

        final Path path = applicationProperties.getExport().getPath();

        log.info("Exporting into: {}", path.toAbsolutePath().toString());


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
            for (final JdlEntity entity : entities) {
                if (!entity.isPureManyToMany()) {
                    out.write(JdlUtils.writeEntity(entity));
                    out.newLine();
                    out.newLine();
                }
            }

            out.newLine();
            out.newLine();
            out.write("// Relations");
            out.newLine();

            for (final JdlEntity entity : entities) {
                for (final JdlRelation relation : entity.getRelations()) {
                    if (relation.getRelationType() == RelationType.ManyToMany) {
                        out.write(JdlUtils.writeRelationPureManyToMany(relation));
                    } else {
                        out.write(JdlUtils.writeRelation(relation));
                    }
                    out.newLine();
                    out.newLine();
                }
            }

            out.newLine();
            out.newLine();
            out.write("// Options");
            out.newLine();

            out.write(JdlUtils.serviceClassAll());
            out.newLine();
            out.write(JdlUtils.paginationAll());
            out.newLine();
            out.write(JdlUtils.mapStructAll());
            out.newLine();
            out.write(JdlUtils.filterAll());
            out.newLine();
            out.newLine();

            out.flush();
        } catch (IOException e) {
            log.error("Error", e);
            throw new IllegalStateException(e);
        }

    }
}
