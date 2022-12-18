package org.blackdread.sqltojava.view.impl;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import org.blackdread.sqltojava.entity.RelationType;
import org.blackdread.sqltojava.entity.impl.JdlRelationImpl;
import org.blackdread.sqltojava.view.JdlRelationView;

@Immutable
@ThreadSafe
public class JdlRelationViewImpl extends JdlRelationImpl implements JdlRelationView {

    /**
     * Constructory based on super
     * @param relationType
     * @param bidirectional
     * @param ownerRequired
     * @param inverseSideRequired
     * @param ownerEntityName
     * @param inverseSideEntityName
     * @param ownerRelationName
     * @param ownerDisplayField
     * @param ownerComment
     * @param inverseSideComment
     * @param inverseSideRelationName
     * @param inverseSideDisplayField
     * @param comment
     */
    public JdlRelationViewImpl(
        RelationType relationType,
        boolean bidirectional,
        boolean ownerRequired,
        boolean inverseSideRequired,
        String ownerEntityName,
        String inverseSideEntityName,
        String ownerRelationName,
        @Nullable String ownerDisplayField,
        @Nullable String ownerComment,
        @Nullable String inverseSideComment,
        @Nullable String inverseSideRelationName,
        @Nullable String inverseSideDisplayField,
        @Nullable String comment
    ) {
        super(
            relationType,
            bidirectional,
            ownerRequired,
            inverseSideRequired,
            ownerEntityName,
            inverseSideEntityName,
            ownerRelationName,
            ownerDisplayField,
            ownerComment,
            inverseSideComment,
            inverseSideRelationName,
            inverseSideDisplayField,
            comment
        );
    }
}
