package org.blackdread.sqltojava.repository;

import java.util.List;
import java.util.Map;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.blackdread.sqltojava.pojo.rowmaper.ColumnInformationRowMapper;
import org.blackdread.sqltojava.pojo.rowmaper.TableInformationRowMapper;
import org.blackdread.sqltojava.pojo.rowmaper.TableRelationInformationRowMapper;
import org.blackdread.sqltojava.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile({ "postgresql" })
public class PureSqlInformationSchemaRepository implements InformationSchemaRepository {

    private static final Logger log = LoggerFactory.getLogger(PureSqlInformationSchemaRepository.class);
    private static String ALL_TABLE_RELATIONAL_INFROMATION;
    private static String FULL_COLUMN_INFORMATION_OF_TABLE;
    private static String ALL_TABLE_INFORMATION;
    private static final TableInformationRowMapper TABLE_INFORMATION_MAPPER = new TableInformationRowMapper();
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public PureSqlInformationSchemaRepository(NamedParameterJdbcTemplate template, ConfigurableEnvironment env) {
        String activeProfile = env.getActiveProfiles()[0];
        /**
         *  TODO Handle mysql and mariadb needing the same sql files.  mysql|mariadb-getAllTableRelationInformation.sql
         *  Find all files ending with getAllTableRelationInformation then filter for match.
         */
        ALL_TABLE_RELATIONAL_INFROMATION = readSqlFileByProfile(activeProfile, "getAllTableRelationInformation");
        FULL_COLUMN_INFORMATION_OF_TABLE = readSqlFileByProfile(activeProfile, "getFullColumnInformationOfTable");
        ALL_TABLE_INFORMATION = readSqlFileByProfile(activeProfile, "getAllTableInformation");
        this.template = template;
    }

    @Override
    public List<TableRelationInformation> getAllTableRelationInformation(final String schemaName) {
        Map<String, ?> paramMap = Map.of("schemaName", schemaName);
        return template.queryForStream(ALL_TABLE_RELATIONAL_INFROMATION, paramMap, new TableRelationInformationRowMapper()).toList();
    }

    @Override
    public List<ColumnInformation> getFullColumnInformationOfTable(final String schemaName, final String tableName) {
        Map<String, ?> paramMap = Map.of("schemaName", schemaName, "tableName", tableName);
        return template.queryForStream(FULL_COLUMN_INFORMATION_OF_TABLE, paramMap, new ColumnInformationRowMapper()).toList();
    }

    public List<TableInformation> getAllTableInformation(final String schemaName) {
        Map<String, ?> paramMap = Map.of("schemaName", schemaName);
        return template.queryForStream(ALL_TABLE_INFORMATION, paramMap, TABLE_INFORMATION_MAPPER).toList();
    }

    private static String readSqlFileByProfile(String activeProfile, String queryName) {
        return ResourceUtil.readString(String.format("sql/%s-%s.sql", activeProfile, queryName));
    }
}
