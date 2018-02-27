package org.blackdread.sqltojava.pojo;

import java.util.Objects;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
public class TableRelationInformation {

    private final String tableName;
    private final String columnName;
    private final String referencedTableName;
    private final String referencedColumnName;

    public TableRelationInformation(final String tableName, final String columnName, final String referencedTableName, final String referencedColumnName) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.referencedTableName = referencedTableName;
        this.referencedColumnName = referencedColumnName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getReferencedTableName() {
        return referencedTableName;
    }

    public String getReferencedColumnName() {
        return referencedColumnName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TableRelationInformation that = (TableRelationInformation) o;
        return Objects.equals(tableName, that.tableName) &&
            Objects.equals(columnName, that.columnName) &&
            Objects.equals(referencedTableName, that.referencedTableName) &&
            Objects.equals(referencedColumnName, that.referencedColumnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, columnName, referencedTableName, referencedColumnName);
    }

    @Override
    public String toString() {
        return "TableRelationInformation{" +
            "tableName='" + tableName + '\'' +
            ", columnName='" + columnName + '\'' +
            ", referencedTableName='" + referencedTableName + '\'' +
            ", referencedColumnName='" + referencedColumnName + '\'' +
            '}';
    }
}
