package org.blackdread.sqltojava.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.blackdread.sqltojava.pojo.rowmaper.SqlServerColumnInformationRowMapper;
import org.blackdread.sqltojava.pojo.rowmaper.SqlServerTableInformationRowMapper;
import org.blackdread.sqltojava.pojo.rowmaper.TableRelationInformationRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile({ "sqlserver" })
public class MsSqlPureSqlInformationSchemaRepository implements InformationSchemaRepository {

    private static final Logger log = LoggerFactory.getLogger(MsSqlPureSqlInformationSchemaRepository.class);
    private static String ALL_TABLE_RELATIONAL_INFROMATION;
    private static String FULL_COLUMN_INFORMATION_OF_TABLE;
    private static String ALL_TABLE_INFORMATION;
    private static final SqlServerTableInformationRowMapper TABLE_INFORMATION_MAPPER = new SqlServerTableInformationRowMapper();
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public MsSqlPureSqlInformationSchemaRepository(NamedParameterJdbcTemplate template, ConfigurableEnvironment env) {
        String activeProfile = env.getActiveProfiles()[0];
        ALL_TABLE_RELATIONAL_INFROMATION = readSqlFileByProfile(activeProfile, "getAllTableRelationInformation");
        FULL_COLUMN_INFORMATION_OF_TABLE = readSqlFileByProfile(activeProfile, "getFullColumnInformationOfTable");
        ALL_TABLE_INFORMATION = readSqlFileByProfile(activeProfile, "getAllTableInformation");
        this.template = template;
    }

    @Override
    public List<TableRelationInformation> getAllTableRelationInformation(final String schemaName) {
        Map<String, ?> paramMap = Map.of("schemaName", schemaName);
        return template.queryForStream(ALL_TABLE_RELATIONAL_INFROMATION, paramMap, new TableRelationInformationRowMapper()).toList();
    }

    @Override
    public List<ColumnInformation> getFullColumnInformationOfTable(final String schemaName, final String tableName) {
        Map<String, ?> paramMap = Map.of("schemaName", schemaName, "tableName", tableName);
        return template.queryForStream(FULL_COLUMN_INFORMATION_OF_TABLE, paramMap, new SqlServerColumnInformationRowMapper()).toList();
    }

    public List<TableInformation> getAllTableInformation(final String schemaName) {
        Map<String, ?> paramMap = Map.of("schemaName", schemaName);
        return template.queryForStream(ALL_TABLE_INFORMATION, paramMap, TABLE_INFORMATION_MAPPER).toList();
    }

    /**
     * Loads a sql query file based on the active profile.
     * For multiple profiles to use the same sql files they should be named as follows:
     * sql/{profile1}_{profile1}-{queryName}.sql
     * See mysql_mariadb-queryName.sql for an example.
     * @param activeProfile
     * @param queryName
     * @return
     */
    private String readSqlFileByProfile(String activeProfile, String queryName) {
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        String pattern = String.format("classpath*:/sql/*%s*-%s.sql", activeProfile, queryName);
        log.info("Using pattern " + pattern);
        try {
            Resource[] resources = resolver.getResources(pattern);
            if (resources.length != 1) {
                throw new IllegalArgumentException(String.format("Found %s when 1 was expected", resources.length));
            }
            Resource r = resources[0];
            log.info("Found resource " + r.getFilename());

            Resource resource = new ClassPathResource("sql/" + r.getFilename());
            InputStream inputStream = resource.getInputStream();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //            return ResourceUtil.readString(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
