package org.blackdread.sqltojava.repository;

import static org.blackdread.sqltojava.jooq.generated.tables.KeyColumnUsage.KEY_COLUMN_USAGE;

import java.util.List;
import org.blackdread.sqltojava.jooq.generated.InformationSchema;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile({ "mysql", "mariadb" })
public class MySqlInformationSchemaRepository implements InformationSchemaRepository {

    private static final Logger log = LoggerFactory.getLogger(MySqlInformationSchemaRepository.class);

    private final DSLContext create;

    @Autowired
    public MySqlInformationSchemaRepository(final DSLContext create) {
        this.create = create;
    }

    public List<TableRelationInformation> getAllTableRelationInformation(final String dbName) {
        /*
        SELECT CONCAT(table_name) AS table_name, CONCAT(column_name) AS column_name, CONCAT(referenced_table_name)
        AS referenced_table_name, CONCAT(referenced_column_name) AS referenced_column_name
        FROM INFORMATION_SCHEMA.key_column_usage WHERE referenced_table_schema = '" . DB_NAME . "'
        AND referenced_table_name IS NOT NULL ORDER BY table_name, column_name
        */
        return create
            .select(
                KEY_COLUMN_USAGE.TABLE_NAME,
                KEY_COLUMN_USAGE.COLUMN_NAME,
                KEY_COLUMN_USAGE.REFERENCED_TABLE_NAME,
                KEY_COLUMN_USAGE.REFERENCED_COLUMN_NAME
            )
            .from(InformationSchema.INFORMATION_SCHEMA.KEY_COLUMN_USAGE)
            .where(KEY_COLUMN_USAGE.REFERENCED_TABLE_SCHEMA.eq(dbName).and(KEY_COLUMN_USAGE.REFERENCED_TABLE_NAME.isNotNull()))
            .orderBy(KEY_COLUMN_USAGE.TABLE_NAME, KEY_COLUMN_USAGE.COLUMN_NAME)
            .fetch()
            .map(this::map);
    }

    public List<ColumnInformation> getFullColumnInformationOfTable(final String dbName, final String tableName) {
        return create
            .resultQuery("SHOW FULL COLUMNS FROM " + dbName + "." + tableName)
            //            .bind(1, tableName)
            .fetch()
            .map(r ->
                new ColumnInformation(
                    (String) r.get("Field"),
                    (String) r.get("Type"),
                    (String) r.get("Null"),
                    (String) r.get("Key"),
                    (String) r.get("Default"),
                    (String) r.get("Comment")
                )
            );
    }

    public List<TableInformation> getAllTableInformation(final String dbName) {
        return create
            .select(InformationSchema.INFORMATION_SCHEMA.TABLES.TABLE_NAME, InformationSchema.INFORMATION_SCHEMA.TABLES.TABLE_COMMENT)
            .from(InformationSchema.INFORMATION_SCHEMA.TABLES)
            .where(InformationSchema.INFORMATION_SCHEMA.TABLES.TABLE_SCHEMA.eq(dbName))
            .fetch()
            .map(r -> new TableInformation(r.value1(), r.value2()));
    }

    private TableRelationInformation map(final Record4<String, String, String, String> r) {
        return new TableRelationInformation(r.value1(), r.value2(), r.value3(), r.value4());
    }
}
