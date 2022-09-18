package org.blackdread.sqltojava.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
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

    @Value("classpath:sql/postgres-getAllTableRelationInformation.sql")
    private Resource getAllTableRelationInformationSql;

    @Value("classpath:sql/postgres-getFullColumnInformationOfTable.sql")
    private Resource getFullColumnInformationOfTableSql;

    @Value("classpath:sql/postgres-getAllTableInformation.sql")
    private Resource getAllTableInformationSql;

    @Override
    public List<TableRelationInformation> getAllTableRelationInformation(final String dbName) {
        String sql = null;
        try {
            File file = getAllTableRelationInformationSql.getFile();
            sql = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return create
            .resultQuery(sql, dbName)
            .fetch()
            .map(
                r ->
                    new TableRelationInformation(
                        (String) r.get("table_name"),
                        (String) r.get("column_name"),
                        (String) r.get("foreign_table_name"),
                        (String) r.get("foreign_column_name")
                        /*public TableRelationInformation(
        final String tableName,
        final String columnName,
        final String referencedTableName,
        final String referencedColumnName
    ) {
            this.tableName = tableName;
            this.columnName = columnName;
            this.referencedTableName = referencedTableName;
            this.referencedColumnName = referencedColumnName;
        }*/

                    )
            );
    }

    @Override
    public List<ColumnInformation> getFullColumnInformationOfTable(final String dbName, final String tableName) {
        String sql = null;
        try {
            File file = getFullColumnInformationOfTableSql.getFile();
            sql = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return create
            .resultQuery(sql, tableName)
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
        String sql = null;
        try {
            File file = getAllTableInformationSql.getFile();
            sql = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return create
            .resultQuery(sql)
            //            .bind(1, tableName)
            .fetch()
            .map(r -> new TableInformation((String) r.get("table_name"), (String) r.get("comment")));
    }

    public List<String> getAllTableName(final String dbName) {
        throw new UnsupportedOperationException();
    }

    private TableRelationInformation map(final Record4<String, String, String, String> r) {
        return new TableRelationInformation(r.value1(), r.value2(), r.value3(), r.value4());
    }
}
