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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("postgresql")
public class PostgresInformationSchemaRepository implements InformationSchemaRepository {
    private static final Logger log = LoggerFactory.getLogger(PostgresInformationSchemaRepository.class);
    public static final String ALL_TABLE_RELATIONAL_INFROMATION = ResourceUtil.readString(
        "sql/postgres-getAllTableRelationInformation.sql"
    );
    public static final String FULL_COLUMN_INFORMATION_OF_TABLE = ResourceUtil.readString(
        "sql/postgres-getFullColumnInformationOfTable.sql"
    );
    public static final String ALL_TABLE_INFORMATION = ResourceUtil.readString("sql/postgres-getAllTableInformation.sql");
    private static final TableInformationRowMapper TABLE_INFORMATION_MAPPER = new TableInformationRowMapper();

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public PostgresInformationSchemaRepository(NamedParameterJdbcTemplate template) {
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
}
