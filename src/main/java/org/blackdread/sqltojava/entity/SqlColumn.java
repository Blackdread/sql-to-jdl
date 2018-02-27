package org.blackdread.sqltojava.entity;

import java.util.Optional;

/**
 * Created by Yoann CAPLAIN on 2017/10/6.
 */
public interface SqlColumn {

    SqlTable getTable();

    /**
     * @return Column name
     */
    String getName();

    /**
     * Is usually a value of type and size like "type(size)"
     *
     * @return Column type
     */
    String getType();

    boolean isPrimaryKey();

    boolean isForeignKey();

    boolean isNullable();

    boolean isUnique();

    Optional<String> getDefaultValue();

    Optional<String> getComment();

}
