package org.blackdread.sqltojava.entity.impl;

import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlFieldEnum;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Optional;

/**
 * <p>Created on 2018/2/9.</p>
 *
 * @author Yoann CAPLAIN
 */
@Immutable
@ThreadSafe
public class JdlFieldImpl implements JdlField {

    private final JdlFieldEnum type;
    private final String name;
    private final String enumEntityName;
    private final boolean isRequired;
    private final String comment;
    private final Integer min;
    private final Integer max;
    private final String pattern;

    public JdlFieldImpl(final JdlFieldEnum type, final String name, final boolean isRequired) {
        this(type, name, isRequired, null, null, null, null, null);
    }

    public JdlFieldImpl(final JdlFieldEnum type, final String name, final boolean isRequired, @Nullable final String comment) {
        this(type, name, isRequired, comment, null, null, null, null);
    }

    public JdlFieldImpl(final JdlFieldEnum type, final String name, final boolean isRequired, @Nullable final String comment, @Nullable final Integer min) {
        this(type, name, isRequired, comment, min, null, null, null);
    }

    public JdlFieldImpl(final JdlFieldEnum type, final String name, final boolean isRequired, @Nullable final String comment, @Nullable final Integer min, @Nullable final Integer max) {
        this(type, name, isRequired, comment, min, max, null, null);
    }

    public JdlFieldImpl(final JdlFieldEnum type, final String name, final boolean isRequired, @Nullable final String comment, @Nullable final Integer min, @Nullable final Integer max, @Nullable final String pattern) {
        this(type, name, isRequired, comment, min, max, pattern, null);
    }

    public JdlFieldImpl(final JdlFieldEnum type, final String name, final boolean isRequired, @Nullable final String comment, @Nullable final Integer min, @Nullable final Integer max, @Nullable final String pattern, @Nullable final String enumEntityName) {
        this.type = type;
        this.name = name;
        this.isRequired = isRequired;
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
        this.min = min;
        this.max = max;
        this.pattern = pattern;
        this.enumEntityName = enumEntityName;
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
        return isRequired;
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
    public String toString() {
        return "JdlFieldImpl{" +
            "type=" + type +
            ", name='" + name + '\'' +
            ", isRequired=" + isRequired +
            ", comment='" + comment + '\'' +
            ", min=" + min +
            ", max=" + max +
            ", pattern='" + pattern + '\'' +
            '}';
    }
}
