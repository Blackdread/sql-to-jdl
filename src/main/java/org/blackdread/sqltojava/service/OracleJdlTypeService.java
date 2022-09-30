package org.blackdread.sqltojava.service;

import static org.blackdread.sqltojava.entity.JdlFieldEnum.*;

import java.util.Map;
import java.util.Optional;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.blackdread.sqltojava.util.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("oracle")
public class OracleJdlTypeService implements SqlJdlTypeService {
    private static final Logger log = LoggerFactory.getLogger(OracleJdlTypeService.class);

    @Override
    public JdlFieldEnum sqlTypeToJdlType(String sqlType) {
        throw new UnsupportedOperationException();
    }
}
