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
@Profile("oracle")
public class OracleJdlTypeService implements SqlJdlTypeService {

    private static final Logger log = LoggerFactory.getLogger(OracleJdlTypeService.class);

    private final ApplicationProperties properties;

    public OracleJdlTypeService(ApplicationProperties properties) {
        this.properties = properties;
    }

    private final Map<String, JdlFieldEnum> typeMap = Map.ofEntries(
        entry("VARCHAR2", STRING),
        entry("NUMBER", INTEGER),
        entry("NUMBER(38)", LONG),
        entry("NUMBER(19,5)", BIG_DECIMAL),
        entry("FLOAT", FLOAT),
        entry("BLOB", BLOB),
        entry("TIMESTAMP", INSTANT),
        entry("DATE", LOCAL_DATE),
        entry("CHAR", STRING),
        entry("CLOB", BLOB),
        entry("NCHAR", STRING),
        entry("NVARCHAR2", STRING),
        entry("RAW", UUID)
    );

    @Override
    public Map<String, JdlFieldEnum> getTypeMap() {
        return this.mergeOverrides(this.typeMap, properties.getJdlTypeOverrides());
    }
}
