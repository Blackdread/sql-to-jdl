package org.blackdread.sqltojava.service.logic;

import com.google.common.collect.Maps;
import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.entity.SqlColumn;
import org.blackdread.sqltojava.entity.SqlTable;
import org.blackdread.sqltojava.entity.impl.SqlColumnImpl;
import org.blackdread.sqltojava.entity.impl.SqlTableImpl;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.blackdread.sqltojava.service.InformationSchemaService;
import org.blackdread.sqltojava.util.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Created on 2018/2/7.</p>
 *
 * @author Yoann CAPLAIN
 */
@Service
@Transactional(readOnly = true)
public class SqlService {

    private static final Logger log = LoggerFactory.getLogger(SqlService.class);

    private final ApplicationProperties applicationProperties;

    private final InformationSchemaService informationSchemaService;

    @Autowired
    public SqlService(final ApplicationProperties applicationProperties, final InformationSchemaService informationSchemaService) {
        this.applicationProperties = applicationProperties;
        this.informationSchemaService = informationSchemaService;
    }

    // @Cacheable does not work when in same class so we loose a bit in efficiency but repo is cached as well so...

    @Cacheable("SqlService.buildTables")
    public List<SqlTable> buildTables() {
        log.debug("buildTables called");
        final List<String> ignoredTableNames = applicationProperties.getIgnoredTableNames();
        return informationSchemaService.getAllTableInformation().stream()
            .filter(table -> !isTableIgnored(ignoredTableNames, table))
            .map(table -> new SqlTableImpl(table.getName(), table.getComment().orElse(null)))
            .collect(Collectors.toList());
    }

    @Cacheable("SqlService.buildColumns")
    public List<SqlColumn> buildColumns() {
        log.debug("buildColumns called");
        return buildTables().stream()
            .map(table -> Maps.immutableEntry(table, informationSchemaService.getFullColumnInformationOfTable(table.getName())))
            .map(entry -> entry.getValue().stream()
                .map(columnInformation ->
                    new SqlColumnImpl(entry.getKey(),
                        columnInformation.getName(),
                        columnInformation.getType(),
                        columnInformation.isPrimary(),
                        isForeignKey(entry.getKey().getName(), columnInformation.getName()),
                        columnInformation.isNullable(),
                        columnInformation.isUnique(),
                        columnInformation.getDefaultValue().orElse(null),
                        columnInformation.getComment())
                )
                .collect(Collectors.toList()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    @Cacheable("SqlService.getTableOfForeignKey")
    public SqlTable getTableOfForeignKey(final SqlColumn column) {
        log.debug("getTableOfForeignKey called: ({}) ({})", column.getTable().getName(), column.getName());
        return informationSchemaService.getAllTableRelationInformation().stream()
            .filter(e -> e.getTableName().equals(column.getTable().getName()))
            .filter(e -> e.getColumnName().equals(column.getName()))
            .findFirst()
            .map(TableRelationInformation::getReferencedTableName)
            .map(tableName -> buildTables().stream().filter(e -> e.getName().equals(tableName)).findFirst())
            .map(sqlTable -> sqlTable.orElseThrow(() -> new IllegalStateException("Table not found or is ignored")))
            .orElseThrow(() -> new IllegalArgumentException("Column is not a foreign key"));
    }

    @Cacheable("SqlService.isEnumTable")
    public boolean isEnumTable(final String tableName) {
        final List<ColumnInformation> table = informationSchemaService.getFullColumnInformationOfTable(tableName);

        if (table.size() != 2)
            return false;

        for (final ColumnInformation column : table) {
            // Our design contract define that id and code/name is an enum table so logic is put here
            if (!column.getName().equalsIgnoreCase("id") && (!column.getName().equalsIgnoreCase("code") && !column.getName().equalsIgnoreCase("name")))
                return false;
        }

        return true;
    }

    /**
     * Check if the column name passed from the table name specified is referencing an enum table
     *
     * @param tableName  Table containing the FK column
     * @param columnName FK column that reference the enum table
     * @return True if this column name is referencing an enum table
     */
    @Cacheable("SqlService.isForeignKeyFromAnEnumTable")
    public boolean isForeignKeyFromAnEnumTable(final String tableName, final String columnName) {
        if (!isForeignKey(tableName, columnName))
            return false;
        return isEnumTable(SqlUtils.removeIdFromEnd(columnName));
    }

    /**
     * @param tableName  Table to check
     * @param columnName Column to check
     * @return True if column is a foreign key
     */
    @Cacheable("SqlService.isForeignKey")
    public boolean isForeignKey(final String tableName, final String columnName) {
        return informationSchemaService.getAllTableRelationInformation().stream()
            .filter(e -> e.getTableName().equals(tableName))
            .filter(e -> e.getColumnName().equals(columnName))
            .findFirst().isPresent();
    }

    /**
     * A pure many to many table is a table with 2 columns and both are foreign keys.
     * <p>Other MtM tables add other columns and treated as real entities with MtO relations</p>
     *
     * @param tableName Table to check
     * @return True if this table is a pure many to many table (2 columns, 2 FK)
     */
    @Cacheable("SqlService.isPureManyToManyTable")
    public boolean isPureManyToManyTable(final String tableName) {
        final List<ColumnInformation> table = informationSchemaService.getFullColumnInformationOfTable(tableName);

        if (table.size() != 2)
            return false;

        for (final ColumnInformation column : table) {
            if (!isForeignKey(tableName, column.getName()))
                return false;
        }

        return true;
    }

    private boolean isTableIgnored(final List<String> ignoredTableNames, final TableInformation table) {
        return ignoredTableNames.contains(table.getName());
    }

}
