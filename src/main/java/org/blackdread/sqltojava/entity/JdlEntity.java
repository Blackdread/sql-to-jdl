package org.blackdread.sqltojava.entity;

import java.util.List;
import java.util.Optional;

public interface JdlEntity {
    String getName();

    String getTableName();

    List<JdlField> getFields();

    /**
     * Add @readOnly to read only entity.
     * https://www.jhipster.tech/jdl/options
     */
    boolean isReadOnly();

    Optional<String> getComment();

    boolean isEnum();

    /**
     * A pure ManyToMany entity only contains 2 columns of foreign keys
     *
     * @return True if this entity represent a pure ManyToMany relation of two other entities
     */
    boolean isPureManyToMany();

    /**
     * @return Relations where this entity is the owner side
     */
    List<JdlRelation> getRelations();
}
