/**
 * hisuda copy right 1.0 
 */
package com.buyi.dal.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.buyi.dal.entity.viewobject.Money;
import com.ibatis.sqlmap.engine.type.BaseTypeHandler;
import com.ibatis.sqlmap.engine.type.TypeHandler;

/**
 * 
 * 
 * MoneyTypeHandle.java
 * 
 * @author cxf128
 */
public class MoneyTypeHandler extends BaseTypeHandler implements TypeHandler {

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, String jdbcType) throws SQLException {
        ps.setInt(i, ((Money) parameter).getAmount());
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.wasNull()) {
            return null;
        }

        Object bigdec = new Money(rs.getInt(columnName));
        return bigdec;
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.wasNull()) {
            return null;
        }
        Object bigdec = new Money(rs.getInt(columnIndex));
        return bigdec;
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.wasNull()) {
            return null;
        }
        Object bigdec = new Money(cs.getInt(columnIndex));
        return bigdec;
    }

    @Override
    public Object valueOf(String s) {
        Integer value = Integer.parseInt(s);
        return new Money(value);
    }

}
