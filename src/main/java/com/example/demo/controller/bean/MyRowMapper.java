package com.example.demo.controller.bean;

import com.example.demo.controller.bean.Curriculum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现RowMapper接口，返回User对象
 * */
public class MyRowMapper implements RowMapper<Curriculum>{

    public boolean isExistColumn(ResultSet rs, String columnName) {
        try {
            if (rs.findColumn(columnName) > 0 ) {
                return true;
            }
        }
        catch (SQLException e) {
            return false;
        }

        return false;
    }

    @Override
    public Curriculum mapRow(ResultSet resultSet, int i) throws SQLException {
//        获取结果集中的数据
        Curriculum curriculum = new Curriculum();
        if(isExistColumn(resultSet,"SNO")){
            String SNO = resultSet.getString("SNO");
            curriculum.setSerialNumber(SNO);
        }

        String CNO = resultSet.getString("CNO");
        String TNO = resultSet.getString("TNO");
        String TName = resultSet.getString("TName");
        String CNAME = resultSet.getString("CNAME");
        String Credit = resultSet.getString("Credit");
//        把数据封装成Curriculum对象

        curriculum.setCourseNumber(CNO);
        curriculum.setTeacherSerial(TNO);
        curriculum.setTeacherName(TName);
        curriculum.setCourseName(CNAME);
        curriculum.setCredit(Credit);
        return curriculum;
    }
}