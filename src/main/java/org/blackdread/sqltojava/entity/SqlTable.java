package org.blackdread.sqltojava.entity;

import java.util.Optional;

public interface SqlTable {
    String getName();

    Optional<String> getComment();

    boolean isUpdatable();
    //    boolean isEnumTable();

    //    List<SqlColumn> getColumns();
}
