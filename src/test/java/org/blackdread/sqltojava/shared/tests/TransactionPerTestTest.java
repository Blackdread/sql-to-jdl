package org.blackdread.sqltojava.shared.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class TransactionPerTestTest extends BaseJdbcContainerTest {

    private static final Logger log = LoggerFactory.getLogger(TransactionPerTestTest.class);
    private static final String rollbackTag = "0.0.0";
    private static final Contexts contexts = new Contexts("integration-test");
    private static final LabelExpression labelExpression = new LabelExpression();
    private static final ResourceAccessor RESOURCE_ACCESSOR = new ClassLoaderResourceAccessor();
    private Liquibase liquibase;

    @Autowired
    private DataSource dataSource;

    @Autowired
    CacheManager cacheManager;

    private Connection connection;

    public DataSource dataSource() {
        return this.dataSource;
    }

    @AfterEach
    public void rollback() throws LiquibaseException {
        if (liquibase != null) {
            rollbackToEmpty();
            liquibase.close();
        }
    }

    @AfterAll
    public static void cleanup() {}

    protected String pathToString(String path) {
        String content = null;
        try {
            content = Files.readString(Path.of(getClass().getResource(path).toURI()));
        } catch (Exception e) {}
        return content;
    }

    protected void evictAllCaches() {
        log.info("Clearing all caches");
        cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

    private Database database(DataSource dataSource) throws LiquibaseException {
        this.connection = DataSourceUtils.getConnection(dataSource);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(this.connection));
        return database;
    }

    protected void runChangeLogFile(String pathChangeLogFile) throws SQLException, LiquibaseException {
        liquibase = createLiquibase(pathChangeLogFile);
        liquibase.update(contexts, labelExpression);
    }

    private Liquibase createLiquibase(String pathChangeLogFile) throws SQLException, LiquibaseException {
        return new Liquibase(pathChangeLogFile, RESOURCE_ACCESSOR, database(dataSource));
    }

    private void rollbackToEmpty() throws LiquibaseException {
        liquibase.rollback(rollbackTag, contexts);
    }
}
