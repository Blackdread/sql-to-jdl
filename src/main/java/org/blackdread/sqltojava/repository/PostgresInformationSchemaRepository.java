package org.blackdread.sqltojava.repository;

import java.util.List;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.blackdread.sqltojava.util.ResourceUtil;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("postgresql")
public class PostgresInformationSchemaRepository implements InformationSchemaRepository {
    private static final Logger log = LoggerFactory.getLogger(PostgresInformationSchemaRepository.class);

    private final DSLContext create;

    @Autowired
    public PostgresInformationSchemaRepository(final DSLContext create) {
        this.create = create;
    }

    public static final String ALL_TABLE_RELATIONAL_INFROMATION = ResourceUtil.readString(
        "sql/postgres-getAllTableRelationInformation.sql"
    );
    public static final String FULL_COLUMN_INFORMATION_OF_TABLE = ResourceUtil.readString(
        "sql/postgres-getFullColumnInformationOfTable.sql"
    );
    public static final String ALL_TABLE_INFORMATION = ResourceUtil.readString("sql/postgres-getAllTableInformation.sql");

    @Override
    public List<TableRelationInformation> getAllTableRelationInformation(final String dbName) {
        return create
            .resultQuery(ALL_TABLE_RELATIONAL_INFROMATION, dbName)
            .fetch()
            .map(
                r ->
                    new TableRelationInformation(
                        (String) r.get("table_name"),
                        (String) r.get("column_name"),
                        (String) r.get("foreign_table_name"),
                        (String) r.get("foreign_column_name")
                    )
            );
    }

    @Override
    public List<ColumnInformation> getFullColumnInformationOfTable(final String dbName, final String tableName) {
        return create
            .resultQuery(FULL_COLUMN_INFORMATION_OF_TABLE, tableName)
            .fetch()
            .map(
                r ->
                    new ColumnInformation(
                        (String) r.get("column_name"),
                        (String) r.get("data_type"),
                        (String) r.get("is_nullable"),
                        (String) r.get("key"),
                        (String) r.get("column_default"),
                        (String) r.get("comment")
                    )
            );
    }

    public List<TableInformation> getAllTableInformation(final String dbName) {
        return create
            .resultQuery(ALL_TABLE_INFORMATION)
            .fetch()
            .map(r -> new TableInformation((String) r.get("table_name"), (String) r.get("comment")));
    }

    public List<String> getAllTableName(final String dbName) {
        throw new UnsupportedOperationException();
    }
}
