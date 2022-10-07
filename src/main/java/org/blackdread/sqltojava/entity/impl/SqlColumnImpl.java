package org.blackdread.sqltojava.entity.impl;

import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.SqlColumn;
import org.blackdread.sqltojava.entity.SqlTable;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
public class SqlColumnImpl implements SqlColumn {

    private final SqlTable table;

    private final String name;

    private final String type;

    private final boolean isPrimaryKey;

    private final boolean isForeignKey;

    private final boolean isNullable;

    private final boolean isUnique;

    private final boolean isNativeEnum;

    private final String defaultValue;

    private final String comment;

    public SqlColumnImpl(
        final SqlTable table,
        final String name,
        final String type,
        final boolean isPrimaryKey,
        final boolean isForeignKey,
        final boolean isNullable,
        final boolean isUnique,
        final boolean isNativeEnum,
        @Nullable final String defaultValue,
        final String comment
    ) {
        this.table = table;
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
        this.isNullable = isNullable;
        this.isUnique = isUnique;
        this.isNativeEnum = isNativeEnum;
        this.defaultValue = (StringUtils.isBlank(defaultValue) || "null".equalsIgnoreCase(comment)) ? null : defaultValue;
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
    }

    @Override
    public SqlTable getTable() {
        return table;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    @Override
    public boolean isForeignKey() {
        return isForeignKey;
    }

    @Override
    public boolean isNullable() {
        return isNullable;
    }

    @Override
    public boolean isUnique() {
        return isUnique;
    }

    @Override
    public boolean isNativeEnum() {
        return isNativeEnum;
    }

    @Override
    public Optional<String> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    @Override
    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SqlColumnImpl sqlColumn = (SqlColumnImpl) o;
        return (Objects.equals(table, sqlColumn.table) && Objects.equals(name, sqlColumn.name) && Objects.equals(type, sqlColumn.type));
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, name, type);
    }

    @Override
    public String toString() {
        return (
            "SqlColumnImpl{" +
            "table=" +
            table +
            ", name='" +
            name +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", isPrimaryKey=" +
            isPrimaryKey +
            ", isForeignKey=" +
            isForeignKey +
            ", isNullable=" +
            isNullable +
            ", isUnique=" +
            isUnique +
            ", isNativeEnum=" +
            isNativeEnum +
            ", defaultValue='" +
            defaultValue +
            '\'' +
            ", comment='" +
            comment +
            '\'' +
            '}'
        );
    }
}
