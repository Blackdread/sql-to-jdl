package org.blackdread.sqltojava.util;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;

import com.google.common.base.CaseFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.SqlColumn;
import org.blackdread.sqltojava.entity.SqlTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SqlUtils {

    private static final Logger log = LoggerFactory.getLogger(SqlUtils.class);

    public static final Pattern COLUMN_TYPE_SIZE_REGEX = Pattern.compile("(^[a-z0-9\\s]+)(\\((\\-?[0-9]+)\\))?$", Pattern.CASE_INSENSITIVE);

    private SqlUtils() {}

    /**
     * @param value A string that might ends with "_id" or "Id"
     * @return Value without any ID suffix
     */
    public static String removeIdFromEnd(final String value) {
        return value.toLowerCase().endsWith("_id")
            ? value.substring(0, value.length() - 3)
            : value.endsWith("Id") ? value.substring(0, value.length() - 2) : value;
    }

    public static String changeToCamelCase(final String value) {
        if (value.contains("_")) {
            return CaseFormat.LOWER_UNDERSCORE.to(LOWER_CAMEL, value);
        } else if (value.equals(value.toUpperCase())) {
            // If the string is in all uppercase, convert it to lowercase
            return value.toLowerCase();
        } else {
            // If the string contains uppercase letters in the middle, split the string into words
            // based on the uppercase letters, then join the words with the first word in lowercase
            // and the rest of the words in their original case
            String[] words = value.split("(?=[A-Z])");
            String res = Stream.of(words).map(word -> word.equals(words[0]) ? word.toLowerCase() : word).collect(Collectors.joining());
            return changeIdToLowerCase(res).replace("ID", "Id");
        }
    }

    public static String changeIdToLowerCase(final String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        if (value.startsWith("iD") && Character.isUpperCase(value.charAt(2))) {
            return "id" + value.substring(2);
        }
        return value;
    }

    public static Map<SqlTable, List<SqlColumn>> groupColumnsByTable(final List<SqlColumn> sqlColumns) {
        return sqlColumns.stream().collect(Collectors.groupingBy(SqlColumn::getTable, Collectors.toList()));
    }

    public static Optional<Integer> parseSqlSize(String value) {
        value = StringUtils.trim(value);
        final Matcher matcher = COLUMN_TYPE_SIZE_REGEX.matcher(value);

        if (matcher.matches()) {
            return Optional.of(Integer.valueOf(matcher.group(3)));
        }
        log.warn("Did not find sql size from: {}", value);
        return Optional.empty();
    }

    public static String parseSqlType(String value) {
        // todo: Oracle Bigint, BigDecimal
        if (value.equals("NUMBER(38)") || value.equals("NUMBER(19,5)")) {
            return value;
        }
        return value.split("\\(")[0];
    }
}
