package org.blackdread.sqltojava.entity;

import java.util.Optional;

/**
 * Created by Yoann CAPLAIN on 2017/10/6.
 */
public interface SqlTable {

    String getName();

    Optional<String> getComment();

//    boolean isEnumTable();

//    List<SqlColumn> getColumns();
}
