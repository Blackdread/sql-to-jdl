package org.blackdread.sqltojava.service;

import org.blackdread.sqltojava.entity.JdlFieldEnum;

public interface SqlJdlTypeService {
    JdlFieldEnum sqlTypeToJdlType(final String sqlType);
}
