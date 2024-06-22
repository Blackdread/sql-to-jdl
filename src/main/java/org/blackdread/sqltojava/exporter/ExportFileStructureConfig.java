package org.blackdread.sqltojava.exporter;

import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.config.ExportFileStructureType;
import org.springframework.stereotype.Service;

@Service
public class ExportFileStructureConfig {

    private final ApplicationProperties applicationProperties;
    private ExportFileStructureType exportFileStructureType;
    private String exportMustacheTemplateFilename;

    public ExportFileStructureConfig(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        exportFileStructureType = applicationProperties.getExport().getExportFileStructureType();
        String newTemplateName = applicationProperties.getExport().getExportMustacheTemplateFilenameOptional();
        exportMustacheTemplateFilename =
            StringUtils.isBlank(newTemplateName) ? exportFileStructureType.getExportMustacheTemplateFilename() : newTemplateName;
    }

    public String getExportMustacheTemplateFilename() {
        return exportMustacheTemplateFilename;
    }

    public ExportFileStructureType getExportFileStructureType() {
        return exportFileStructureType;
    }
}
