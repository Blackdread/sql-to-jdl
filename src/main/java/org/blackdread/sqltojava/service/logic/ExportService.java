package org.blackdread.sqltojava.service.logic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;
import org.blackdread.sqltojava.util.JdlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    /**
     * Exports jdl to string
     * @param entities
     * @return
     */
    public String exportString(final List<JdlEntity> entities) {
        String nl = System.lineSeparator();
        if (entities.isEmpty()) {
            log.error("No entities were found for which JDL is to be generated. Please review console logs");
            return "";
        }

        // 1. Entities
        // 2. Relations
        // 3. Options

        try (StringWriter out = new StringWriter()) {
            for (final JdlEntity entity : entities) {
                if (!entity.isPureManyToMany()) {
                    out.write(JdlUtils.writeEntity(entity));
                    out.write(nl);
                    out.write(nl);
                }
            }

            out.write(nl);
            out.write(nl);
            out.write("// Relations");
            out.write(nl);

            for (final JdlEntity entity : entities) {
                for (final JdlRelation relation : entity.getRelations()) {
                    if (relation.getRelationType() == RelationType.ManyToMany) {
                        out.write(JdlUtils.writeRelationPureManyToMany(relation));
                    } else {
                        out.write(JdlUtils.writeRelation(relation));
                    }
                    out.write(nl);
                    out.write(nl);
                }
            }

            out.write(nl);
            out.write(nl);
            out.write("// Options");
            out.write(nl);

            out.write(JdlUtils.serviceClassAll());
            out.write(nl);
            out.write(JdlUtils.paginationAll());
            out.write(nl);
            out.write(JdlUtils.mapStructAll());
            out.write(nl);
            out.write(JdlUtils.filterAll());
            out.write(nl);
            out.write(nl);

            out.flush();
            return out.toString();
        } catch (IOException e) {
            log.error("Error", e);
            throw new IllegalStateException(e);
        }
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
