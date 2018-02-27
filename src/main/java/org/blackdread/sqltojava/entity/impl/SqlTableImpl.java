package org.blackdread.sqltojava.entity.impl;

import org.blackdread.sqltojava.entity.SqlTable;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.SqlTable;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
public class SqlTableImpl implements SqlTable, Comparable<SqlTable> {

    private final String name;

    private final String comment;

    public SqlTableImpl(final String name, @Nullable final String comment) {
        this.name = Objects.requireNonNull(name);
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SqlTableImpl sqlTable = (SqlTableImpl) o;
        return Objects.equals(name, sqlTable.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "SqlTableImpl{" +
            "name='" + name + '\'' +
            ", comment='" + comment + '\'' +
            '}';
    }

    @Override
    public int compareTo(final SqlTable o) {
        return name.compareTo(o.getName());
    }
}
