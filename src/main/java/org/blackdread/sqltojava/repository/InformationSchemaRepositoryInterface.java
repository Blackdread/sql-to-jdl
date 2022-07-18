package org.blackdread.sqltojava.repository;

import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;

import java.util.List;

public interface InformationSchemaRepositoryInterface {

    List<TableRelationInformation> getAllTableRelationInformation(final String dbName);
    List<ColumnInformation> getFullColumnInformationOfTable(final String dbName, final String tableName);
    List<TableInformation> getAllTableInformation(final String dbName);
    List<String> getAllTableName(final String dbName);

}
