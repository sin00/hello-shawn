package com.ericsson.li.HelloJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<VehicleCity> {

	public VehicleCity mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		VehicleCity user = new VehicleCity();  
        user.setId(rs.getInt("id"));  
        user.setName(rs.getString("name"));  
        user.setPasswd(rs.getString("passwd"));
		return user;
	}

}
