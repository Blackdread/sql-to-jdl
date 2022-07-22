package org.blackdread.sqltojava.util;

public final class JdbcUtil {

    /**
     * This method assumes the jdbc url starts with "jdbc:database_type:"
     * @param jdbcUrl
     * @return
     */
    public static String getDatabaseTypeFromJdbcUrl(String jdbcUrl) {
        String[] parts = jdbcUrl.split(":");
        assert parts[0].equals("jdbc");
        return parts[1];
    }

}
