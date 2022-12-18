package org.blackdread.sqltojava.entity.impl;

import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;

@Immutable
@ThreadSafe
public class JdlRelationImpl implements JdlRelation, Comparable<JdlRelation> {

    private final RelationType relationType;

    private final boolean bidirectional;
    private final boolean ownerRequired;
    private final boolean inverseSideRequired;

    private final String ownerEntityName;
    private final String inverseSideEntityName;

    private final String ownerRelationName;
    private final String ownerDisplayField;

    private final String ownerComment;
    private final String inverseSideComment;
    private final String inverseSideRelationName;
    private final String inverseSideDisplayField;

    private final String comment;

    public JdlRelationImpl(
        final RelationType relationType,
        final boolean bidirectional,
        final boolean ownerRequired,
        final boolean inverseSideRequired,
        final String ownerEntityName,
        final String inverseSideEntityName,
        final String ownerRelationName,
        @Nullable final String ownerDisplayField,
        @Nullable final String ownerComment,
        @Nullable final String inverseSideComment,
        @Nullable final String inverseSideRelationName,
        @Nullable final String inverseSideDisplayField,
        @Nullable final String comment
    ) {
        this.relationType = relationType;
        this.bidirectional = bidirectional;
        this.ownerRequired = ownerRequired;
        this.inverseSideRequired = inverseSideRequired;

        this.ownerEntityName = ownerEntityName;
        this.inverseSideEntityName = inverseSideEntityName;

        this.ownerRelationName = ownerRelationName;
        this.ownerDisplayField =
            (StringUtils.isBlank(ownerDisplayField) || "null".equalsIgnoreCase(ownerDisplayField)) ? null : ownerDisplayField;

        this.ownerComment = (StringUtils.isBlank(ownerComment) || "null".equalsIgnoreCase(ownerComment)) ? null : ownerComment;
        this.inverseSideComment =
            (StringUtils.isBlank(inverseSideComment) || "null".equalsIgnoreCase(inverseSideComment)) ? null : inverseSideComment;
        this.inverseSideRelationName =
            (StringUtils.isBlank(inverseSideRelationName) || "null".equalsIgnoreCase(inverseSideRelationName))
                ? null
                : inverseSideRelationName;
        this.inverseSideDisplayField =
            (StringUtils.isBlank(inverseSideDisplayField) || "null".equalsIgnoreCase(inverseSideDisplayField))
                ? null
                : inverseSideDisplayField;
        this.comment = (StringUtils.isBlank(comment) || "null".equalsIgnoreCase(comment)) ? null : comment;
    }

    @Override
    public RelationType getRelationType() {
        return relationType;
    }

    @Override
    public boolean isBidirectional() {
        return bidirectional;
    }

    @Override
    public boolean isOwnerRequired() {
        return ownerRequired;
    }

    @Override
    public boolean isInverseSideRequired() {
        return inverseSideRequired;
    }

    @Override
    public Optional<String> getOwnerComment() {
        return Optional.ofNullable(ownerComment);
    }

    @Override
    public Optional<String> getInverseSideComment() {
        return Optional.ofNullable(inverseSideComment);
    }

    @Override
    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    @Override
    public String getOwnerEntityName() {
        return ownerEntityName;
    }

    @Override
    public String getInverseSideEntityName() {
        return inverseSideEntityName;
    }

    @Override
    public String getOwnerRelationName() {
        return ownerRelationName;
    }

    @Override
    public Optional<String> getInverseSideRelationName() {
        return Optional.ofNullable(inverseSideRelationName);
    }

    @Override
    public Optional<String> getOwnerDisplayField() {
        return Optional.ofNullable(ownerDisplayField);
    }

    @Override
    public Optional<String> getInverseSideDisplayField() {
        return Optional.ofNullable(inverseSideDisplayField);
    }

    @Override
    public int compareTo(final JdlRelation o) {
        return this.ownerEntityName.compareTo(o.getOwnerEntityName());
    }
}
