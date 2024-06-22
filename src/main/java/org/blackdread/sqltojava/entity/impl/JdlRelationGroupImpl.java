package org.blackdread.sqltojava.entity.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import org.blackdread.sqltojava.entity.JdlRelation;
import org.blackdread.sqltojava.entity.JdlRelationGroup;
import org.blackdread.sqltojava.entity.RelationType;

@Immutable
@ThreadSafe
public class JdlRelationGroupImpl implements JdlRelationGroup, Comparable<JdlRelationGroup> {

    private final RelationType relationType;
    private final List<JdlRelation> relations;

    public JdlRelationGroupImpl(RelationType relationType, List<JdlRelation> relations) {
        this.relationType = relationType;
        this.relations = relations;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public List<JdlRelation> getRelations() {
        return relations;
    }

    @Override
    public int compareTo(final JdlRelationGroup o) {
        return this.relationType.compareTo(o.getRelationType());
    }

    @Override
    public String toString() {
        return (
            "JdlRelationGroupImpl{" +
            "relationType=" +
            relationType +
            ", relations=" +
            relations.stream().map(e -> e.getOwnerEntityName()).collect(Collectors.joining()) +
            '}'
        );
    }
}
