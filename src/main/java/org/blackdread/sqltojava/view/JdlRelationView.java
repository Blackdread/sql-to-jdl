package org.blackdread.sqltojava.view;

import java.util.Optional;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;

/**
 * Methods for JDL relations in the view
 */
public interface JdlRelationView extends JdlRelation, JdlCommentView {
    default String getType() {
        return getRelationType().toJdl();
    }

    default boolean isPureManyToMany() {
        return getRelationType() == RelationType.ManyToMany;
    }

    default String getOwnerDocComment() {
        return getDocComment(getOwnerComment().orElse(null));
    }

    default boolean hasComments() {
        return getOwnerComment().isPresent() || getInverseSideComment().isPresent();
    }

    default String getInverseDocComment() {
        return getDocComment(getInverseSideComment().orElse(null));
    }

    default String getOwnerConfig() {
        return "{" + getOwnerRelationName() + getRelationConfig(getOwnerDisplayField(), isOwnerRequired()) + "}";
    }

    default String getInverseConfig() {
        if (isBidirectional()) {
            return (
                "{" +
                getInverseSideRelationName().orElse("") +
                getRelationConfig(getInverseSideDisplayField(), isInverseSideRequired()) +
                "}"
            );
        } else {
            return null;
        }
    }

    default String getDisplayField(Optional<String> relation) {
        return relation.map(displayName -> "(" + displayName + ")").orElse("");
    }

    default String getRelationConfig(Optional<String> relation, boolean required) {
        return getDisplayField(relation) + ((required) ? " required" : "");
    }
}
