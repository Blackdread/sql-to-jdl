package org.blackdread.sqltojava.service;

import static org.blackdread.sqltojava.entity.JdlFieldEnum.UNSUPPORTED;

import java.util.Map;
import java.util.Optional;
import org.blackdread.sqltojava.entity.JdlFieldEnum;
import org.blackdread.sqltojava.util.SqlUtils;

public interface SqlJdlTypeService {
    Map<String, JdlFieldEnum> getTypeMap();

    default JdlFieldEnum sqlTypeToJdlType(final String sqlType) {
        String typeName = SqlUtils.parseSqlType(sqlType);
        JdlFieldEnum jdlType = Optional.ofNullable(getTypeMap().get(typeName)).orElse(UNSUPPORTED);
        //orElseThrow(() -> new IllegalStateException("Unknown type: " + typeName));
        return jdlType;
    }
}
