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
@Profile("oracle")
public class OracleInformationSchemaRepository implements InformationSchemaRepository {
    private static final Logger log = LoggerFactory.getLogger(OracleInformationSchemaRepository.class);

    private final DSLContext create;

    @Autowired
    public OracleInformationSchemaRepository(final DSLContext create) {
        this.create = create;
    }

    @Override
    public List<TableRelationInformation> getAllTableRelationInformation(String dbName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ColumnInformation> getFullColumnInformationOfTable(String dbName, String tableName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TableInformation> getAllTableInformation(String dbName) {
        throw new UnsupportedOperationException();
    }

}
