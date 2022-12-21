package org.blackdread.sqltojava.pojo;

import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class TableInformation {

    private String schema;
    private final String name;
    private final String comment;
    private final boolean isUpdateable;
    private final String type;

    public TableInformation(final String name, final boolean isUpdateable, String type, final String comment) {
        this.name = name;
        this.isUpdateable = isUpdateable;
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    public String getSchema() {
        return schema;
    }

    public Boolean isUpdateable() {
        return isUpdateable;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return ("TableInformation{" + "name='" + name + '\'' + ", comment='" + comment + '\'' + '}');
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TableInformation that = (TableInformation) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
