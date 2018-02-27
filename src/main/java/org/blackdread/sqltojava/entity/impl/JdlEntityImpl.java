package org.blackdread.sqltojava.entity.impl;

import com.google.common.collect.ImmutableList;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlRelation;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.Optional;

/**
 * <p>Created on 2018/2/9.</p>
 *
 * @author Yoann CAPLAIN
 */
@Immutable
@ThreadSafe
public class JdlEntityImpl implements JdlEntity, Comparable<JdlEntity> {

    private final String name;

    private final List<JdlField> fields;

    private final String comment;

    private final boolean isEnum;

    private final boolean isPureManyToMany;

    private final List<JdlRelation> relations;

    public JdlEntityImpl(final String name, final List<JdlField> fields, @Nullable final String comment, final boolean isEnum, final boolean isPureManyToMany, final List<JdlRelation> relations) {
        this.name = name;
        this.fields = ImmutableList.copyOf(fields);
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
        this.isEnum = isEnum;
        this.isPureManyToMany = isPureManyToMany;
        this.relations = ImmutableList.copyOf(relations);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<JdlField> getFields() {
        return fields;
    }

    @Override
    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    @Override
    public boolean isEnum() {
        return isEnum;
    }

    @Override
    public boolean isPureManyToMany() {
        return isPureManyToMany;
    }

    @Override
    public List<JdlRelation> getRelations() {
        return relations;
    }

    @Override
    public String toString() {
        return "JdlEntityImpl{" +
            "name='" + name + '\'' +
            ", fields=" + fields +
            ", comment='" + comment + '\'' +
            ", isEnum=" + isEnum +
            ", relations=" + relations +
            '}';
    }

    @Override
    public int compareTo(final JdlEntity o) {
        return name.compareTo(o.getName());
    }
}
