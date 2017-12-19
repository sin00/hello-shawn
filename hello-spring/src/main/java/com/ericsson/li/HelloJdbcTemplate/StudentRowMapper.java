package com.ericsson.li.HelloJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StudentRowMapper implements RowMapper<Student> {

	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Student student = new Student();  
        student.setId(rs.getInt("id"));  
        student.setName(rs.getString("name"));  
        student.setPasswd(rs.getString("passwd"));
        student.setTime(rs.getDate("update_time"));
		return student;
	}

}
