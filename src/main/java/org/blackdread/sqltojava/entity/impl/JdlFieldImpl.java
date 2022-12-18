package org.blackdread.sqltojava.entity.impl;

import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlFieldEnum;

@Immutable
@ThreadSafe
public class JdlFieldImpl implements JdlField {

    private final JdlFieldEnum type;
    private final String name;
    private final String enumEntityName;
    private final boolean required;
    private final String comment;
    private final Integer min;
    private final Integer max;
    private final String pattern;
    private final Boolean nativeEnum;
    private final Boolean unique;
    private final Boolean primaryKey;

    public JdlFieldImpl(
        final JdlFieldEnum type,
        final String name,
        final boolean required,
        @Nullable final String comment,
        @Nullable final Integer min,
        @Nullable final Integer max,
        @Nullable final String pattern,
        @Nullable final String enumEntityName,
        final boolean nativeEnum,
        final boolean unique,
        final boolean primaryKey
    ) {
        this.type = type;
        this.name = name;
        this.required = required;
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
        this.min = min;
        this.max = max;
        this.pattern = pattern;
        this.enumEntityName = enumEntityName;
        this.nativeEnum = nativeEnum;
        this.unique = unique;
        this.primaryKey = primaryKey;
    }

    @Override
    public JdlFieldEnum getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getEnumEntityName() {
        return Optional.ofNullable(enumEntityName);
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    @Override
    public Optional<Integer> getMin() {
        return Optional.ofNullable(min);
    }

    @Override
    public Optional<Integer> getMax() {
        return Optional.ofNullable(max);
    }

    @Override
    public Optional<String> getPattern() {
        return Optional.ofNullable(pattern);
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    @Override
    public boolean isNativeEnum() {
        return nativeEnum;
    }

    @Override
    public String toString() {
        return (
            "JdlFieldImpl{" +
            "type=" +
            type +
            ", name='" +
            name +
            '\'' +
            ", enumEntityName='" +
            enumEntityName +
            '\'' +
            ", isRequired=" +
            required +
            ", comment='" +
            comment +
            '\'' +
            ", min=" +
            min +
            ", max=" +
            max +
            ", pattern='" +
            pattern +
            '\'' +
            ", isNativeEnum=" +
            nativeEnum +
            '}'
        );
    }
}
