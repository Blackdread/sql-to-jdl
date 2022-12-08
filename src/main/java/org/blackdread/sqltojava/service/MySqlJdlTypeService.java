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
@Profile({ "mysql", "mariadb" })
public class MySqlJdlTypeService implements SqlJdlTypeService {

    private static final Logger log = LoggerFactory.getLogger(MySqlJdlTypeService.class);

    private final ApplicationProperties properties;

    public MySqlJdlTypeService(ApplicationProperties properties) {
        this.properties = properties;
    }

    private final Map<String, JdlFieldEnum> typeMap = Map.ofEntries(
        entry("varchar", STRING),
        entry("char", STRING),
        entry("text", STRING),
        entry("tinytext", STRING),
        entry("mediumtext", TEXT_BLOB),
        entry("longtext", TEXT_BLOB),
        entry("tinyblob", BLOB),
        entry("blob", BLOB),
        entry("mediumblob", BLOB),
        entry("longblob", BLOB),
        entry("smallint", INTEGER),
        entry("mediumint", INTEGER),
        entry("int", INTEGER),
        entry("bigint", LONG),
        entry("float", FLOAT),
        entry("double", DOUBLE),
        entry("date", LOCAL_DATE),
        entry("enum", ENUM),
        entry("set", ENUM),
        entry("boolean", BOOLEAN),
        entry("tinyint", BOOLEAN),
        entry("bit", BOOLEAN),
        entry("decimal", BIG_DECIMAL),
        entry("datetime", INSTANT),
        entry("timestamp", INSTANT),
        /**
         * Not support by base jhipster but to export database which has this type.
         * See:
         * https://blog.ippon.fr/2017/12/04/la-technologie-spatiale-au-service-de-jhipster/
         * https://github.com/chegola/jhipster-spatial
         * https://stackoverflow.com/questions/50122390/integration-of-postgis-with-jhipster
         **/
        entry("geometry", GEOMETRY_AS_TEXT),
        /**
         * Not support by base jhipster but to export database which has this type.
         */
        entry("json", JSON_AS_TEXT),
        entry("time", TIME_AS_TEXT),
        entry("year", YEAR_AS_TEXT)
    );

    @Override
    public Map<String, JdlFieldEnum> getTypeMap() {
        return mergeOverrides(this.typeMap, properties.getJdlTypeOverrides());
    }
}
