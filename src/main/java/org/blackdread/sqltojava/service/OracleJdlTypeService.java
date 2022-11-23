package org.blackdread.sqltojava.service;

import java.util.Map;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("oracle")
public class OracleJdlTypeService implements SqlJdlTypeService {

    private static final Logger log = LoggerFactory.getLogger(OracleJdlTypeService.class);

    @Override
    public Map<String, JdlFieldEnum> getTypeMap() {
        return null;
    }
}
