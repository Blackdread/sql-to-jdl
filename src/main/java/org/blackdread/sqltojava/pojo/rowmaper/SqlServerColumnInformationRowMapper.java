package org.blackdread.sqltojava.pojo.rowmaper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.blackdread.sqltojava.pojo.ColumnInformation;
import org.springframework.jdbc.core.RowMapper;

public class SqlServerColumnInformationRowMapper implements RowMapper<ColumnInformation> {

    @Override
    public ColumnInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ColumnInformation(
            rs.getString("column_name"),
            rs.getString("data_type"),
            rs.getString("is_nullable"),
            rs.getString("keye"),
            rs.getString("column_default"),
            rs.getInt("ordinal_position"),
            ""
        );
    }
}
