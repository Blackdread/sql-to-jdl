package org.blackdread.sqltojava.service;

import static org.blackdread.sqltojava.entity.JdlFieldEnum.*;

import java.util.Map;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("postgresql")
public class PostgresJdlTypeService implements SqlJdlTypeService {

    private static final Logger log = LoggerFactory.getLogger(PostgresJdlTypeService.class);
    private final Map<String, JdlFieldEnum> typeMap = Map.ofEntries(
        Map.entry("boolean", BOOLEAN),
        Map.entry("date", LOCAL_DATE),
        Map.entry("time without time zone", TIME_AS_TEXT),
        Map.entry("time with time zone", TIME_AS_TEXT),
        Map.entry("timestamp without time zone", INSTANT),
        Map.entry("timestamp with time zone", ZONED_DATE_TIME),
        Map.entry("real", FLOAT),
        Map.entry("double precision", DOUBLE),
        Map.entry("smallint", INTEGER),
        Map.entry("integer", INTEGER),
        Map.entry("bigint", LONG),
        Map.entry("money", BIG_DECIMAL),
        Map.entry("numeric", BIG_DECIMAL),
        Map.entry("character", STRING),
        Map.entry("character varying", STRING),
        Map.entry("text", STRING_UNBOUNDED),
        Map.entry("bytea", BLOB),
        Map.entry("json", JSON_AS_TEXT),
        Map.entry("uuid", UUID)
        //Map.entry("interval", null),
        //Map.entry("jsonb", null),
        //Map.entry("jsonpath", null),
        //Map.entry("macaddr", STRING),
        //Map.entry("macaddr8", STRING),
        //Map.entry("xml", STRING)
    );

    @Override
    public Map<String, JdlFieldEnum> getTypeMap() {
        return this.typeMap;
    }
}
