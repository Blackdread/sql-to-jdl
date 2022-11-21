package org.blackdread.sqltojava.config;

/**
 * Determines how undefined SQL column types ar handled
 */
public enum UndefinedJdlTypeHandlingEnum {
    /**
     * Throw an error
     */
    ERROR,

    /**
     * Skip writing the field to JDL
     */
    SKIP,

    /**
     * Write Unsupported as type and require manual cleanup
     */
    UNSUPPORTED,
}
