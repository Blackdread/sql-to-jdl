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


    public List<TableRelationInformation> getAllTableRelationInformation(final String dbName) {
        throw new IllegalStateException("todo");
    }

    public List<ColumnInformation> getFullColumnInformationOfTable(final String dbName, final String tableName) {
        throw new IllegalStateException("todo");
    }

    public List<TableInformation> getAllTableInformation(final String dbName) {
        throw new IllegalStateException("todo");
    }

    public List<String> getAllTableName(final String dbName) {
        throw new IllegalStateException("todo");
    }

    private TableRelationInformation map(final Record4<String, String, String, String> r) {
        return new TableRelationInformation(r.value1(), r.value2(), r.value3(), r.value4());
    }
}
