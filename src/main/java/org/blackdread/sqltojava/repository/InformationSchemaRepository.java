package org.blackdread.sqltojava.repository;

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

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
@Repository
public class InformationSchemaRepository {

    private static final Logger log = LoggerFactory.getLogger(InformationSchemaRepository.class);

    private final DSLContext create;

    @Autowired
    public InformationSchemaRepository(final DSLContext create) {
        this.create = create;
    }


    public List<TableRelationInformation> getAllTableRelationInformation(final String dbName) {
        /*
        SELECT CONCAT(table_name) AS table_name, CONCAT(column_name) AS column_name, CONCAT(referenced_table_name)
        AS referenced_table_name, CONCAT(referenced_column_name) AS referenced_column_name
        FROM INFORMATION_SCHEMA.key_column_usage WHERE referenced_table_schema = '" . DB_NAME . "'
        AND referenced_table_name IS NOT NULL ORDER BY table_name, column_name
        */
        return create.resultQuery("SELECT rel_kcu.table_name, rel_kcu.column_name, tco.table_name, kcu.column_name " +
            "from information_schema.table_constraints tco " +
            "         join information_schema.key_column_usage kcu " +
            "              on tco.constraint_schema = kcu.constraint_schema " +
            "                  and tco.constraint_name = kcu.constraint_name " +
            "         join information_schema.referential_constraints rco " +
            "              on tco.constraint_schema = rco.constraint_schema " +
            "                  and tco.constraint_name = rco.constraint_name " +
            "         join information_schema.key_column_usage rel_kcu " +
            "              on rco.unique_constraint_schema = rel_kcu.constraint_schema " +
            "                  and rco.unique_constraint_name = rel_kcu.constraint_name " +
            "                  and kcu.ordinal_position = rel_kcu.ordinal_position " +
            "where tco.constraint_type = 'FOREIGN KEY'" +
            "and tco.constraint_schema = '" + dbName +"'")
            .fetch()
            .map(r -> new TableRelationInformation((String) r.get(0), (String) r.get(1),(String) r.get(2),(String) r.get(3)));
    }

    public List<ColumnInformation> getFullColumnInformationOfTable(final String dbName, final String tableName) {
        return create.resultQuery("SELECT * " +
            "FROM information_schema.columns " +
            "WHERE table_schema = '" + dbName + "' " +
            "  AND table_name   = '"+ tableName +"'")
//            .bind(1, tableName)
            .fetch()
            .map(r -> {
                String columnName = (String) r.get("column_name");
                return new ColumnInformation(
                    columnName,
                    (String) r.get("data_type"),
                    ((String) r.get("is_nullable")).equalsIgnoreCase("YES"),
                    columnName.equalsIgnoreCase("ID"),
                    (String) r.get("column_default"));
            });
    }

    public List<TableInformation> getAllTableInformation(final String dbName) {
        return getAllTableName(dbName).stream()
            .map(r -> new TableInformation(r, r))
            .collect(Collectors.toList());
    }

    public List<String> getAllTableName(final String dbName) {
        return create.resultQuery("select information_schema.TABLES.table_name " +
            " from information_schema.TABLES" +
            " where information_schema.TABLES.TABLE_SCHEMA = '" + dbName + "'")
            .fetch().map(record -> (String) record.get(0));
    }

    private TableRelationInformation map(final Record4<String, String, String, String> r) {
        return new TableRelationInformation(r.value1(), r.value2(), r.value3(), r.value4());
    }
}
