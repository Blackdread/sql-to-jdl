package org.blackdread.sqltojava.service;

import static java.util.Map.entry;
import static org.blackdread.sqltojava.entity.JdlFieldEnum.*;

import java.util.Map;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("postgresql")
public class PostgresJdlTypeService implements SqlJdlTypeService {

    private static final Logger log = LoggerFactory.getLogger(PostgresJdlTypeService.class);

    private final ApplicationProperties properties;

    public PostgresJdlTypeService(ApplicationProperties properties) {
        this.properties = properties;
    }

    private final Map<String, JdlFieldEnum> typeMap = Map.ofEntries(
        entry("boolean", BOOLEAN),
        entry("date", LOCAL_DATE),
        entry("time without time zone", TIME_AS_TEXT),
        entry("time with time zone", TIME_AS_TEXT),
        entry("timestamp without time zone", INSTANT),
        entry("timestamp with time zone", ZONED_DATE_TIME),
        entry("real", FLOAT),
        entry("double precision", DOUBLE),
        entry("smallint", INTEGER),
        entry("integer", INTEGER),
        entry("bigint", LONG),
        entry("money", BIG_DECIMAL),
        entry("numeric", BIG_DECIMAL),
        entry("character", STRING),
        entry("character varying", STRING),
        entry("text", STRING_UNBOUNDED),
        entry("bytea", BLOB),
        entry("json", JSON_AS_TEXT),
        entry("uuid", UUID)
        //Map.entry("interval", null),
        //Map.entry("jsonb", null),
        //Map.entry("jsonpath", null),
        //Map.entry("macaddr", STRING),
        //Map.entry("macaddr8", STRING),
        //Map.entry("xml", STRING)
    );

    @Override
    public Map<String, JdlFieldEnum> getTypeMap() {
        return mergeOverrides(this.typeMap, properties.getJdlTypeOverrides());
    }
}
