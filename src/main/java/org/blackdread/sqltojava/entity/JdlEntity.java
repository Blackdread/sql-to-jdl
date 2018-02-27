package org.blackdread.sqltojava.entity;

import java.util.List;
import java.util.Optional;

/**
 * Created by Yoann CAPLAIN on 2017/10/6.
 */
public interface JdlEntity {

    String getName();

    List<JdlField> getFields();

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
