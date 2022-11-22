package org.blackdread.sqltojava.pojo.rowmaper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.blackdread.sqltojava.pojo.TableInformation;
import org.springframework.jdbc.core.RowMapper;

public class TableInformationRowMapper implements RowMapper<TableInformation> {

    @Override
    public TableInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TableInformation(rs.getString("table_name"), rs.getBoolean("is_updatable"), rs.getString("comment"));
    }
}
