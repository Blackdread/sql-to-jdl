package org.blackdread.sqltojava.pojo;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

public class ColumnInformation {

    private final String name;
    private final String type;
    private final boolean isNullable;
    private final boolean isPrimary;
    private final boolean isUnique;
    private final String defaultValue;
    private final int ordinalPosition;
    private final String comment;

    public ColumnInformation(
        final String name,
        final String type,
        final boolean isNullable,
        final boolean isPrimary,
        final boolean isUnique,
        final String defaultValue,
        final int ordinalPosition,
        final String comment
    ) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
        this.isPrimary = isPrimary;
        this.isUnique = isUnique;
        if (StringUtils.isBlank(defaultValue) || "null".equalsIgnoreCase(defaultValue)) {
            this.defaultValue = null;
        } else {
            this.defaultValue = defaultValue;
        }
        this.ordinalPosition = ordinalPosition;
        this.comment = comment;
    }

    public ColumnInformation(
        final String name,
        final String type,
        final String nullValue,
        final String keyValue,
        final String defaultValue,
        int ordinalPosition,
        final String comment
    ) {
        this(
            name,
            type,
            "yes".equalsIgnoreCase(nullValue),
            "pri".equalsIgnoreCase(keyValue),
            "uni".equalsIgnoreCase(keyValue),
            defaultValue,
            ordinalPosition,
            comment
        );
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public Optional<String> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return (
            "ColumnInformation{" +
            "name='" +
            name +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", isNullable=" +
            isNullable +
            ", isPrimary=" +
            isPrimary +
            ", isUnique=" +
            isUnique +
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
