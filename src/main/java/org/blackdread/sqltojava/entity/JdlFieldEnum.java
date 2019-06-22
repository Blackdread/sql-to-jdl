package org.blackdread.sqltojava.entity;

import com.google.common.base.CaseFormat;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
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
     * Defined here to allow to have a pattern set for TIME type of SQL that jdl does not support by default
     */
    TIME_AS_TEXT,
    /**
     * Defined here to allow to have a pattern set for GEOMETRY type of SQL that jdl does not support by default
     */
    GEOMETRY_AS_TEXT;

    public String toCamelUpper() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name());
    }
}
