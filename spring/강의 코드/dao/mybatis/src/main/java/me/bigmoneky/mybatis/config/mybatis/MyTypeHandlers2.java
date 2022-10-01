package me.bigmoneky.mybatis.config.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.TypeHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MyTypeHandlers2 implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String aLong, JdbcType jdbcType) throws SQLException {
        return;
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        log.info("들어왔나??");
        return rs.getString(columnName);

    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        log.info("들어왔나??");
        return rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("들어왔나??");
        return cs.getString(columnIndex);
    }
}
