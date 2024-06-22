package org.blackdread.sqltojava.view.impl;

import java.util.List;
import java.util.Optional;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.RelationType;
import org.blackdread.sqltojava.entity.impl.JdlRelationGroupImpl;

public class JdlRelationGroupViewImpl extends JdlRelationGroupImpl {

    public JdlRelationGroupViewImpl(RelationType relationType, List<JdlRelation> relations) {
        super(relationType, relations);
    }
}
