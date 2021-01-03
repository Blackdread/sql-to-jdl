package org.blackdread.sqltojava.repository;

import org.blackdread.sqltojava.jooq.generated.InformationSchema;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.name;

/**
 * <p>Created on 2021/01/02.</p>
 *
 * @author Yoann CAPLAIN
 */
@Repository
public class MsSqlInformationSchemaRepository {

    private static final Logger log = LoggerFactory.getLogger(MsSqlInformationSchemaRepository.class);

    private final DSLContext create;

    @Autowired
    public MsSqlInformationSchemaRepository(final DSLContext create) {
        this.create = create;
    }

    // See https://docs.microsoft.com/en-us/sql/relational-databases/system-information-schema-views/system-information-schema-views-transact-sql?view=sql-server-2017

    public List<TableRelationInformation> getAllTableRelationInformation(final String dbName) {
        throw new IllegalStateException("todo");
    }

    public List<ColumnInformation> getFullColumnInformationOfTable(final String dbName, final String tableName) {

        // TODO not finished

        return create.resultQuery("select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = '{0}' and TABLE_NAME = '{1}' order by ORDINAL_POSITION", name(dbName), name(tableName))
            .fetch()
            .map(r -> new ColumnInformation(
                (String) r.get("COLUMN_NAME"),
                (String) r.get("DATA_TYPE"),
                (String) r.get("COLLATION_NAME"),
                (boolean) r.get("IS_NULLABLE"),
                false,
                false,
                (String) r.get("COLUMN_DEFAULT"),
                "",
                ""
            ));
    }

    public List<TableInformation> getAllTableInformation(final String dbName) {
        return getAllTableName(dbName)
            .stream()
            .map(it -> new TableInformation(it, null))
            .collect(Collectors.toList());
    }

    public List<String> getAllTableName(final String dbName) {
//        "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'dbName'"
        return create.select(
            InformationSchema.INFORMATION_SCHEMA.TABLES.TABLE_NAME)
            .from(InformationSchema.INFORMATION_SCHEMA.TABLES)
            .where(InformationSchema.INFORMATION_SCHEMA.TABLES.TABLE_SCHEMA.eq(dbName))
            .fetch()
            .getValues(InformationSchema.INFORMATION_SCHEMA.TABLES.TABLE_NAME);
    }

    private TableRelationInformation map(final Record4<String, String, String, String> r) {
        return new TableRelationInformation(r.value1(), r.value2(), r.value3(), r.value4());
    }
}
