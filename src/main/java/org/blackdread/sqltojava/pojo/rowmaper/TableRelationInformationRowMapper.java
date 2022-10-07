package org.blackdread.sqltojava.pojo.rowmaper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.blackdread.sqltojava.pojo.TableRelationInformation;
import org.springframework.jdbc.core.RowMapper;

public class TableRelationInformationRowMapper implements RowMapper<TableRelationInformation> {

    @Override
    public TableRelationInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TableRelationInformation(
            rs.getString("table_name"),
            rs.getString("column_name"),
            rs.getString("foreign_table_name"),
            rs.getString("foreign_column_name")
        );
    }
}
