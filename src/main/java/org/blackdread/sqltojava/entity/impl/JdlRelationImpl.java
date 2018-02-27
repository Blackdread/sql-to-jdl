package org.blackdread.sqltojava.entity.impl;

import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Optional;

/**
 * <p>Created on 2018/2/12.</p>
 *
 * @author Yoann CAPLAIN
 */
@Immutable
@ThreadSafe
public class JdlRelationImpl implements JdlRelation, Comparable<JdlRelation> {

    private final RelationType relationType;

    private final boolean isBidirectional;
    private final boolean isOwnerRequired;
    private final boolean isInverseSideRequired;

    private final String ownerEntityName;
    private final String inverseSideEntityName;

    private final String ownerRelationName;
    private final String ownerDisplayField;

    private final String ownerComment;
    private final String inverseSideComment;
    private final String inverseSideRelationName;
    private final String inverseDisplayField;

    private final String extraRelationComment;


    public JdlRelationImpl(final RelationType relationType, final boolean isBidirectional, final boolean isOwnerRequired, final boolean isInverseSideRequired, final String ownerEntityName, final String inverseSideEntityName, final String ownerRelationName) {
        this(relationType, isBidirectional, isOwnerRequired, isInverseSideRequired, ownerEntityName, inverseSideEntityName, ownerRelationName, null, null, null, null, null, null);
    }

    public JdlRelationImpl(final RelationType relationType, final boolean isBidirectional, final boolean isOwnerRequired, final boolean isInverseSideRequired, final String ownerEntityName, final String inverseSideEntityName, final String ownerRelationName, @Nullable final String ownerDisplayField) {
        this(relationType, isBidirectional, isOwnerRequired, isInverseSideRequired, ownerEntityName, inverseSideEntityName, ownerRelationName, ownerDisplayField, null, null, null, null, null);
    }

    public JdlRelationImpl(final RelationType relationType, final boolean isBidirectional, final boolean isOwnerRequired, final boolean isInverseSideRequired, final String ownerEntityName, final String inverseSideEntityName, final String ownerRelationName, @Nullable final String ownerDisplayField, @Nullable final String ownerComment) {
        this(relationType, isBidirectional, isOwnerRequired, isInverseSideRequired, ownerEntityName, inverseSideEntityName, ownerRelationName, ownerDisplayField, ownerComment, null, null, null, null);
    }

    public JdlRelationImpl(final RelationType relationType, final boolean isBidirectional, final boolean isOwnerRequired, final boolean isInverseSideRequired, final String ownerEntityName, final String inverseSideEntityName, final String ownerRelationName, @Nullable final String ownerDisplayField, @Nullable final String ownerComment, @Nullable final String inverseSideComment) {
        this(relationType, isBidirectional, isOwnerRequired, isInverseSideRequired, ownerEntityName, inverseSideEntityName, ownerRelationName, ownerDisplayField, ownerComment, inverseSideComment, null, null, null);
    }

    public JdlRelationImpl(final RelationType relationType, final boolean isBidirectional, final boolean isOwnerRequired, final boolean isInverseSideRequired, final String ownerEntityName, final String inverseSideEntityName, final String ownerRelationName, @Nullable final String ownerDisplayField, @Nullable final String ownerComment, @Nullable final String inverseSideComment, @Nullable final String inverseSideRelationName) {
        this(relationType, isBidirectional, isOwnerRequired, isInverseSideRequired, ownerEntityName, inverseSideEntityName, ownerRelationName, ownerDisplayField, ownerComment, inverseSideComment, inverseSideRelationName, null, null);
    }

    public JdlRelationImpl(final RelationType relationType, final boolean isBidirectional, final boolean isOwnerRequired, final boolean isInverseSideRequired, final String ownerEntityName, final String inverseSideEntityName, final String ownerRelationName, @Nullable final String ownerDisplayField, @Nullable final String ownerComment, @Nullable final String inverseSideComment, @Nullable final String inverseSideRelationName, @Nullable final String inverseDisplayField) {
        this(relationType, isBidirectional, isOwnerRequired, isInverseSideRequired, ownerEntityName, inverseSideEntityName, ownerRelationName, ownerDisplayField, ownerComment, inverseSideComment, inverseSideRelationName, inverseDisplayField, null);
    }

    public JdlRelationImpl(final RelationType relationType, final boolean isBidirectional, final boolean isOwnerRequired, final boolean isInverseSideRequired, final String ownerEntityName, final String inverseSideEntityName, final String ownerRelationName, @Nullable final String ownerDisplayField, @Nullable final String ownerComment, @Nullable final String inverseSideComment, @Nullable final String inverseSideRelationName, @Nullable final String inverseDisplayField, @Nullable final String extraRelationComment) {
        this.relationType = relationType;
        this.isBidirectional = isBidirectional;
        this.isOwnerRequired = isOwnerRequired;
        this.isInverseSideRequired = isInverseSideRequired;

        this.ownerEntityName = ownerEntityName;
        this.inverseSideEntityName = inverseSideEntityName;

        this.ownerRelationName = ownerRelationName;
        this.ownerDisplayField = (StringUtils.isBlank(ownerDisplayField) || "null".equalsIgnoreCase(ownerDisplayField)) ? null : ownerDisplayField;

        this.ownerComment = (StringUtils.isBlank(ownerComment) || "null".equalsIgnoreCase(ownerComment)) ? null : ownerComment;
        this.inverseSideComment = (StringUtils.isBlank(inverseSideComment) || "null".equalsIgnoreCase(inverseSideComment)) ? null : inverseSideComment;
        this.inverseSideRelationName = (StringUtils.isBlank(inverseSideRelationName) || "null".equalsIgnoreCase(inverseSideRelationName)) ? null : inverseSideRelationName;
        this.inverseDisplayField = (StringUtils.isBlank(inverseDisplayField) || "null".equalsIgnoreCase(inverseDisplayField)) ? null : inverseDisplayField;
        this.extraRelationComment = (StringUtils.isBlank(extraRelationComment) || "null".equalsIgnoreCase(extraRelationComment)) ? null : extraRelationComment;
    }

    @Override
    public RelationType getRelationType() {
        return relationType;
    }

    @Override
    public boolean isBidirectional() {
        return isBidirectional;
    }

    @Override
    public boolean isOwnerRequired() {
        return isOwnerRequired;
    }

    @Override
    public boolean isInverseSideRequired() {
        return isInverseSideRequired;
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
    public Optional<String> getExtraRelationComment() {
        return Optional.ofNullable(extraRelationComment);
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
        return Optional.ofNullable(inverseDisplayField);
    }

    @Override
    public int compareTo(final JdlRelation o) {
        return this.ownerEntityName.compareTo(o.getOwnerEntityName());
    }
}
