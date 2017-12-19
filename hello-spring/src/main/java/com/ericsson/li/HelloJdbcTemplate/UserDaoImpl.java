package com.ericsson.li.HelloJdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class UserDaoImpl implements UserDao {
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	 public void create(){ //test_user  
	        jdbcTemplate.execute("create table test_user (id integer, name varchar(20),passwd varchar(255))");  
	    }  
	 
	 public void drop(){ //test_user  
	        jdbcTemplate.execute("drop table test_user");
	    } 

	 public void save(VehicleCity user) {  
	        //Assert.isNull(user, "user is not null"); 
		    Assert.notNull(user, "user is not null");
	        jdbcTemplate.update("insert into test_user(id, name, passwd) values(?,?,?)",   
	                new Object[]{user.getId(), user.getName(),user.getPasswd()});  
	    } 
	 
	    public void save2(VehicleCity user) {  
	        //Assert.isNull(user, "user is not null");  
	    	Assert.notNull(user, "user is not null");
	        jdbcTemplate.update(  
	                "insert into test_user(id,name,passwd) values(?,?,?)",   
	                new Object[]{user.getId(), user.getName(),user.getPasswd()},   
	                new int[]{java.sql.Types.INTEGER, java.sql.Types.VARCHAR,java.sql.Types.VARCHAR}  
	                );  
	    } 
	    
	    public void save3(final VehicleCity user) {  
	        //Assert.isNull(user, "user is not null");  
	    	Assert.notNull(user, "user is not null");       
	        jdbcTemplate.update("insert into test_user(id, name,passwd) values(?,?,?)",   
	                new PreparedStatementSetter(){                
	                    public void setValues(PreparedStatement ps) throws SQLException {
	                    	ps.setInt(1, user.getId());
	                        ps.setString(2, user.getName());  
	                        ps.setString(3, user.getPasswd());  
	                    }  
	        });  
	          
	    } 
	 
	public int deleteUser(int id) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(  
                "delete from test_user where id = ?",   
                new Object[]{id},   
                new int[]{java.sql.Types.INTEGER}); 
		return 0;
	}
	public void update(final VehicleCity user) {  
        jdbcTemplate.update(  
                "update test_user set name=?,passwd=? where id =?",   
                new PreparedStatementSetter(){  
                    public void setValues(PreparedStatement ps) throws SQLException {  
                        ps.setString(1, user.getName());  
                        ps.setString(2, user.getPasswd());  
                        ps.setInt(3, user.getId());  
                    }  
                }  
        );  
    }  
	
	public List<VehicleCity> findUsers() {
		String sql = "select * from test_user";
		Object[] params = null;
		/*
		 如果UserRowMapper类只使用一次，单独为其创建一个类多余，可以使用匿名类
		 jdbcTemplate.query(sql, params,  
                new RowMapper<User>() {  
                    @Override  
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {  
                        User user = new User();  
                        user.setId(rs.getInt("id"));  
                        user.setUsername(rs.getString("username"));  
                        user.setPassword(rs.getString("passwd"));  
                        return user;  
                    }  
                }); 
		 **/
		return jdbcTemplate.query(sql, new UserRowMapper());
	}
	
 
}
