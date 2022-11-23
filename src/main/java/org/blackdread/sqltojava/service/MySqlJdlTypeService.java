package org.blackdread.sqltojava.service;

import static org.blackdread.sqltojava.entity.JdlFieldEnum.*;

import java.util.Map;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "mysql", "mariadb" })
public class MySqlJdlTypeService implements SqlJdlTypeService {

    private static final Logger log = LoggerFactory.getLogger(MySqlJdlTypeService.class);

    private final Map<String, JdlFieldEnum> typeMap = Map.ofEntries(
        Map.entry("varchar", STRING),
        Map.entry("char", STRING),
        Map.entry("text", STRING),
        Map.entry("tinytext", STRING),
        Map.entry("mediumtext", TEXT_BLOB),
        Map.entry("longtext", TEXT_BLOB),
        Map.entry("tinyblob", BLOB),
        Map.entry("blob", BLOB),
        Map.entry("mediumblob", BLOB),
        Map.entry("longblob", BLOB),
        Map.entry("smallint", INTEGER),
        Map.entry("mediumint", INTEGER),
        Map.entry("int", INTEGER),
        Map.entry("bigint", LONG),
        Map.entry("float", FLOAT),
        Map.entry("double", DOUBLE),
        Map.entry("date", LOCAL_DATE),
        Map.entry("enum", ENUM),
        Map.entry("set", ENUM),
        Map.entry("boolean", BOOLEAN),
        Map.entry("tinyint", BOOLEAN),
        Map.entry("bit", BOOLEAN),
        Map.entry("decimal", BIG_DECIMAL),
        Map.entry("datetime", INSTANT),
        Map.entry("timestamp", INSTANT),
        /**
         * Not support by base jhipster but to export database which has this type.
         * See:
         * https://blog.ippon.fr/2017/12/04/la-technologie-spatiale-au-service-de-jhipster/
         * https://github.com/chegola/jhipster-spatial
         * https://stackoverflow.com/questions/50122390/integration-of-postgis-with-jhipster
         **/
        Map.entry("geometry", GEOMETRY_AS_TEXT),
        /**
         * Not support by base jhipster but to export database which has this type.
         */
        Map.entry("json", JSON_AS_TEXT),
        Map.entry("time", TIME_AS_TEXT),
        Map.entry("year", YEAR_AS_TEXT)
    );

    @Override
    public Map<String, JdlFieldEnum> getTypeMap() {
        return this.typeMap;
    }
}
