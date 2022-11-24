package org.blackdread.sqltojava.pojo;

import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
public class TableInformation {

    private String schema;

    private String name;

    private String comment;

    private boolean isUpdateable;

    public TableInformation(final String name) {
        this(name, false, null);
    }

    public TableInformation(final String name, final boolean isUpdateable, final String comment) {
        this.name = name;
        this.isUpdateable = isUpdateable;
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
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
