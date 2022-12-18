package org.blackdread.sqltojava.entity.impl;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.JdlEntity;
import org.blackdread.sqltojava.entity.JdlField;
import org.blackdread.sqltojava.entity.JdlRelation;

@Immutable
@ThreadSafe
public class JdlEntityImpl implements JdlEntity, Comparable<JdlEntity> {

    private final String name;

    private final String tableName;

    private final List<JdlField> fields;

    private final String comment;

    private final boolean readOnly;

    private final boolean enumEntity;

    private final boolean pureManyToMany;

    private final List<JdlRelation> relations;

    public JdlEntityImpl(
        final String name,
        final String tableName,
        final List<JdlField> fields,
        @Nullable final String comment,
        final boolean enumEntity,
        final boolean readOnly,
        final boolean pureManyToMany,
        final List<JdlRelation> relations
    ) {
        this.name = name;
        this.tableName = tableName;
        this.fields = ImmutableList.copyOf(fields);
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
        this.enumEntity = enumEntity;
        this.readOnly = readOnly;
        this.pureManyToMany = pureManyToMany;
        this.relations = ImmutableList.copyOf(relations);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTableName() {
        return tableName;
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
    public boolean isEnumEntity() {
        return enumEntity;
    }

    @Override
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public boolean isPureManyToMany() {
        return pureManyToMany;
    }

    @Override
    public List<JdlRelation> getRelations() {
        return relations;
    }

    @Override
    public String toString() {
        return (
            "JdlEntityImpl{" +
            "name='" +
            name +
            '\'' +
            "tableName='" +
            tableName +
            '\'' +
            ", fields=" +
            fields +
            ", comment='" +
            comment +
            '\'' +
            ", readOnly=" +
            readOnly +
            ", isEnum=" +
            enumEntity +
            ", relations=" +
            relations +
            '}'
        );
    }

    @Override
    public int compareTo(final JdlEntity o) {
        return name.compareTo(o.getName());
    }
}
