package com.kc.webdemo01.util;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.text.SimpleDateFormat;


@MappedJdbcTypes({JdbcType.TIMESTAMP})
@MappedTypes({String.class})
public class StringDateTypeHandler implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter,
                             JdbcType jdbcType) throws SQLException {

        ps.setTimestamp(i, Timestamp.valueOf(parameter));
    }


    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp(columnName));
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp(columnIndex));
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getString(columnIndex);
    }
}
