package org.blackdread.sqltojava.config;

public enum ExportFileStructureType {
    SEPARATED("application", "Every entity, relationship are separated"),
    GROUPED_RELATIONS_SEPARATE_VIEWS(
        "application-grouped-relations-separate-views",
        "Relations are grouped by type, views  are separated from entites after entites and relations declarations"
    ),
    RELATIONS_BEFORE_VIEWS_SEPARATE_VIEWS(
        "application-entities-relations-views",
        "Relations are after entities and before views, views  are separated from entites after entites and relations declarations"
    );

    private final String exportMustacheTemplateFilename;
    private final String comment;

    ExportFileStructureType(String exportMustacheTemplateFilename, String comment) {
        this.exportMustacheTemplateFilename = exportMustacheTemplateFilename;
        this.comment = comment;
    }

    public String getExportMustacheTemplateFilename() {
        return exportMustacheTemplateFilename;
    }

    public String getComment() {
        return comment;
    }
}
