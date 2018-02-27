package org.blackdread.sqltojava.util;

import com.google.common.base.CaseFormat;
import org.blackdread.sqltojava.entity.SqlColumn;
import org.blackdread.sqltojava.entity.SqlTable;
import org.apache.commons.lang3.StringUtils;
import org.blackdread.sqltojava.entity.SqlColumn;
import org.blackdread.sqltojava.entity.SqlTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class SqlUtils {

    private static final Logger log = LoggerFactory.getLogger(SqlUtils.class);

    private static final Pattern COLUMN_TYPE_SIZE_REGEX = Pattern.compile("(^[a-z]+)\\(([0-9]+)\\)$", Pattern.CASE_INSENSITIVE);

    private SqlUtils() {
    }

    /**
     * @param value A string that might ends with "_id" or "Id"
     * @return Value without any ID suffix
     */
    public static String removeIdFromEnd(final String value) {
        return value.endsWith("_id") ?
            value.substring(0, value.length() - 3) : value.endsWith("Id") ?
            value.substring(0, value.length() - 2) : value;
    }

    public static String changeToCamelCase(final String value) {
        return CaseFormat.LOWER_UNDERSCORE.to(LOWER_CAMEL, value);
    }

    public static Map<SqlTable, List<SqlColumn>> groupColumnsByTable(final List<SqlColumn> sqlColumns) {
        return sqlColumns.stream()
            .collect(Collectors.groupingBy(SqlColumn::getTable, Collectors.toList()));
    }

    public static Optional<Integer> parseSqlSize(String value) {
        value = StringUtils.trim(value);
        final Matcher matcher = COLUMN_TYPE_SIZE_REGEX.matcher(value);

        if (matcher.matches()) {
            return Optional.of(Integer.valueOf(matcher.group(2)));
        }
        log.warn("Did not find sql size from: {}", value);
        return Optional.empty();
    }

}
