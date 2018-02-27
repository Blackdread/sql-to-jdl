package org.blackdread.sqltojava.entity;

import java.util.Optional;

/**
 * Created by Yoann CAPLAIN on 2017/10/6.
 */
public interface JdlRelation {

    RelationType getRelationType();

    boolean isBidirectional();

    boolean isOwnerRequired();

    boolean isInverseSideRequired();

    Optional<String> getOwnerComment();

    Optional<String> getInverseSideComment();

    /**
     * A comment that can be set to be displayed on top of the relation declaration, to help review the generated
     *
     * @return an extra comment
     */
    Optional<String> getExtraRelationComment();

    /**
     * OwnerSideEntityName{ownerRelationName(ownerDisplayField) required} to InverseSideEntityName{inverseSideRelationName(inverseSideDisplayField)}
     *
     * @return Entity name of owner side
     */
    String getOwnerEntityName();

    /**
     * OwnerSideEntityName{ownerRelationName(ownerDisplayField) required} to InverseSideEntityName{inverseSideRelationName(inverseSideDisplayField)}
     *
     * @return Entity name of inverse side
     */
    String getInverseSideEntityName();

    /**
     * @return Represent the "relationship name" on the owner side
     */
    String getOwnerRelationName();

    /**
     * OwnerSideEntityName{ownerRelationName(ownerDisplayField) required} to InverseSideEntityName{inverseSideRelationName(inverseSideDisplayField)}
     *
     * @return Represent the "relationship name" on the inverse side
     */
    Optional<String> getInverseSideRelationName();

    /**
     * OwnerSideEntityName{ownerRelationName(ownerDisplayField) required} to InverseSideEntityName{inverseSideRelationName(inverseSideDisplayField)}
     *
     * @return Represent the "display field name" on the owner side
     */
    Optional<String> getOwnerDisplayField();

    /**
     * OwnerSideEntityName{ownerRelationName(ownerDisplayField) required} to InverseSideEntityName{inverseSideRelationName(inverseSideDisplayField)}
     *
     * @return Represent the "display field name" on the inverse side
     */
    Optional<String> getInverseSideDisplayField();

}
