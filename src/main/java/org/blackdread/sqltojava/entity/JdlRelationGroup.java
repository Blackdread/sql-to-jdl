package org.blackdread.sqltojava.entity;

import java.util.List;

public interface JdlRelationGroup {
    RelationType getRelationType();
    List<JdlRelation> getRelations();
}
