package org.blackdread.sqltojava.service;

import static java.util.Map.entry;
import static org.blackdread.sqltojava.entity.JdlFieldEnum.*;
import static org.blackdread.sqltojava.entity.JdlFieldEnum.UUID;
import static org.blackdread.sqltojava.util.SqlUtils.COLUMN_TYPE_SIZE_REGEX;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.blackdread.sqltojava.entity.SqlColumn;
import org.blackdread.sqltojava.util.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("sqlserver")
public class MsSqlJdlTypeService implements SqlJdlTypeService {

    private static final Logger log = LoggerFactory.getLogger(MsSqlJdlTypeService.class);
    public static final int VARCHAR_MAX_LENGTH = 8000;
    public static final int NVARCHAR_MAX_LENGTH = 4000;
    public static final int COLUMN_TYPE_MAX_LENGTH_MAX_VALUE = -1;

    private final ApplicationProperties properties;

    public MsSqlJdlTypeService(ApplicationProperties properties) {
        this.properties = properties;
    }

    private final Map<String, JdlFieldEnum> typeMap = Map.ofEntries(
        entry("bit", BOOLEAN),
        entry("date", LOCAL_DATE),
        entry("time", TIME_AS_TEXT),
        entry("datetime", INSTANT),
        entry("datetime2", INSTANT),
        entry("smalldatetime", INSTANT),
        entry("datetimeoffset", ZONED_DATE_TIME),
        entry("real", FLOAT),
        entry("float", DOUBLE),
        entry("smallint", INTEGER),
        entry("int", INTEGER),
        entry("bigint", LONG),
        entry("tinyint", INTEGER),
        entry("money", BIG_DECIMAL),
        entry("numeric", BIG_DECIMAL),
        entry("decimal", BIG_DECIMAL),
        entry("char", STRING), //char(max) is string with length up to 8000
        entry("varchar", STRING), //varchar(max) is string with length up to 8000
        entry("nvarchar", STRING), //nvarchar(max) is string with length up to 4000
        entry("nchar", STRING), //nchar(max) is string with length up to 4000
        entry("text", STRING_UNBOUNDED),
        entry("ntext", STRING_UNBOUNDED), // Added conversion for ntext (deprecated sql server type)
        entry("binary", BLOB),
        entry("varbinary", BLOB),
        entry("image", IMAGE_BLOB),
        entry("varbinary(max)", BLOB),
        entry("varchar(max)", TEXT_BLOB),
        entry("nvarchar(max)", TEXT_BLOB),
        entry("uniqueidentifier", UUID)
    );

    @Override
    public Map<String, JdlFieldEnum> getTypeMap() {
        return mergeOverrides(this.typeMap, properties.getJdlTypeOverrides());
    }

    @Override
    public JdlFieldEnum sqlTypeToJdlType(String sqlType) {
        sqlType = StringUtils.trim(sqlType);
        final Matcher matcher = COLUMN_TYPE_SIZE_REGEX.matcher(sqlType);

        String typeName = null;
        if (matcher.matches()) {
            Integer max = matcher.group(3) == null ? null : Integer.valueOf(matcher.group(3));
            typeName = matcher.group(1).toLowerCase();
            if (ObjectUtils.isNotEmpty(max)) {
                if (max == COLUMN_TYPE_MAX_LENGTH_MAX_VALUE && typeName.equalsIgnoreCase("varbinary")) {
                    typeName = "varbinary(max)".toLowerCase();
                } else if (max == COLUMN_TYPE_MAX_LENGTH_MAX_VALUE && typeName.equalsIgnoreCase("varchar")) {
                    typeName = "varchar(max)".toLowerCase();
                } else if (max == COLUMN_TYPE_MAX_LENGTH_MAX_VALUE && typeName.equalsIgnoreCase("nvarchar")) {
                    typeName = "nvarchar(max)".toLowerCase();
                }
            }
        }
        return Optional.ofNullable(getTypeMap().get(typeName)).orElse(UNSUPPORTED);
    }

    @Override
    public Integer calculateStringMaxLength(SqlColumn column) {
        return SqlUtils.parseSqlSize(column.getType()).orElse(1);
        //        String columnType = SqlUtils.parseSqlType(column.getType());
        //        if ("VARCHAR".equalsIgnoreCase(columnType) && max == COLUMN_TYPE_MAX_VALUE) {
        //            return VARCHAR_MAX_LENGTH;
        //        }
        //        if ("NVARCHAR".equalsIgnoreCase(columnType) && max == COLUMN_TYPE_MAX_VALUE) {
        //            return NVARCHAR_MAX_LENGTH;
        //        }
        //        return SqlJdlTypeService.super.calculateStringMaxLength(column);
    }
}
