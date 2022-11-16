package org.blackdread.sqltojava.repository;

import java.util.List;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;

public interface InformationSchemaRepository {
    List<TableRelationInformation> getAllTableRelationInformation(final String dbName);
    List<ColumnInformation> getFullColumnInformationOfTable(final String dbName, final String tableName);
    List<TableInformation> getAllTableInformation(final String dbName);
}
