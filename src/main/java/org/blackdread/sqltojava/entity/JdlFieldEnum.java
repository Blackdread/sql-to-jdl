package org.blackdread.sqltojava.entity;

import com.google.common.base.CaseFormat;

public enum JdlFieldEnum {
    STRING,
    INTEGER,
    LONG,
    BIG_DECIMAL,
    FLOAT,
    DOUBLE,
    ENUM,
    BOOLEAN,
    LOCAL_DATE,
    ZONED_DATE_TIME,
    INSTANT,
    BLOB,
    ANY_BLOB,
    IMAGE_BLOB,
    TEXT_BLOB,

    /**
     * Used for native enums from DBs, values are defined in the DB so can be extracted.
     *
     * @deprecated not sure yet, this should be checked via extra methods and not a type
     */
    ENUM_NATIVE,

    /**
     * Defined here to allow to have a pattern set for TIME type of SQL that jdl does not support by default
     */
    TIME_AS_TEXT,
    /**
     * Defined here to allow to have a pattern set for YEAR type of SQL that jdl does not support by default
     */
    YEAR_AS_TEXT,
    /**
     * Defined here to allow to have a pattern set for GEOMETRY type of SQL that jdl does not support by default
     */
    GEOMETRY_AS_TEXT,
    /**
     * Defined here to allow to have a string without max length set for json type of SQL that jdl does not support by default
     */
    JSON_AS_TEXT,

    UUID,

    /**
     * Defined here to allow to have a string without max length set for postgres text type
     */
    STRING_UNBOUNDED,

    /**
     * Defined here to allow to jdl to generate with unsupported fields rather than failing with an error.
     */
    UNSUPPORTED;

    public String toCamelUpper() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name());
    }
}
