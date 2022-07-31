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
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class MyTypeHandlers implements TypeHandler<Long> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Long aLong, JdbcType jdbcType) throws SQLException {
        return;
    }

    @Override
    public Long getResult(ResultSet rs, String columnName) throws SQLException {
        log.info("들어왔나??");
        return Timestamp.valueOf(rs.getString(columnName)).getTime();

    }

    @Override
    public Long getResult(ResultSet rs, int columnIndex) throws SQLException {
        log.info("들어왔나??");
        return Timestamp.valueOf(rs.getString(columnIndex)).getTime();
    }

    @Override
    public Long getResult(CallableStatement cs, int columnIndex) throws SQLException {
        log.info("들어왔나??");
        return Timestamp.valueOf(cs.getString(columnIndex)).getTime();
    }
}
