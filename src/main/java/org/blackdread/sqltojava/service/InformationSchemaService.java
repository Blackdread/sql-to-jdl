package org.blackdread.sqltojava.service;

import org.blackdread.sqltojava.config.ApplicationProperties;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.blackdread.sqltojava.repository.InformationSchemaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Created on 2018/2/8.</p>
 *
 * @author Yoann CAPLAIN
 */
@Service
@Transactional(readOnly = true)
public class InformationSchemaService {

    private static final Logger log = LoggerFactory.getLogger(InformationSchemaService.class);

    private final ApplicationProperties applicationProperties;

    private final InformationSchemaRepository informationSchemaRepository;

    public InformationSchemaService(final ApplicationProperties applicationProperties, final InformationSchemaRepository informationSchemaRepository) {
        this.applicationProperties = applicationProperties;
        this.informationSchemaRepository = informationSchemaRepository;
    }

    @Cacheable("InformationSchemaService.getAllTableRelationInformation")
    public List<TableRelationInformation> getAllTableRelationInformation() {
        log.debug("getAllTableRelationInformation called");
        return informationSchemaRepository.getAllTableRelationInformation(applicationProperties.getDatabaseToExport());
    }

    @Cacheable("InformationSchemaService.getFullColumnInformationOfTable")
    public List<ColumnInformation> getFullColumnInformationOfTable(final String tableName) {
        log.debug("getFullColumnInformationOfTable called for table: {}", tableName);
        return informationSchemaRepository.getFullColumnInformationOfTable(applicationProperties.getDatabaseToExport(), tableName);
    }

    @Cacheable("InformationSchemaService.getAllTableInformation")
    public List<TableInformation> getAllTableInformation() {
        log.debug("getAllTableInformation called");
        return informationSchemaRepository.getAllTableInformation(applicationProperties.getDatabaseToExport());
    }

    @Cacheable("InformationSchemaService.getAllTableName")
    public List<String> getAllTableName() {
        log.debug("getAllTableName called");
        return informationSchemaRepository.getAllTableName(applicationProperties.getDatabaseToExport());
    }

}
