package com.ericsson.li.HelloJdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.util.Assert;

public class StudentDao extends JdbcDaoSupport {
	// getJdbcTemplate
	public void create() { // test_student
		this.getJdbcTemplate().execute("create table test_student(id integer, name varchar(20), passwd varchar(255), update_time timestamp)");
	}

	public void drop() { // test_student
		this.getJdbcTemplate().execute("drop table test_student");
	}

	public int save(Student student) {
		// Assert.isNull(student, "student is not null");
		Assert.notNull(student, "student is not null");
		return this.getJdbcTemplate().update("insert into test_student(id, name, passwd, update_time) values(?,?,?,?)",
				new Object[] { student.getId(), student.getName(), student.getPasswd(),new java.sql.Timestamp(student.getTime().getTime())});
	}

	public int save2(Student student) {
		// Assert.isNull(student, "student is not null");
		Assert.notNull(student, "student is not null");
		return this.getJdbcTemplate().update("insert into test_student(id,name,passwd, update_time) values(?,?,?,?)",
				new Object[] { student.getId(), student.getName(), student.getPasswd(), new java.sql.Timestamp(student.getTime().getTime()) },
				new int[] { java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP });
	}

	public void save3(final Student student) {
		// Assert.isNull(student, "student is not null");
		Assert.notNull(student, "student is not null");
		this.getJdbcTemplate().update("insert into test_student(id, name,passwd,update_time) values(?,?,?,?)",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setInt(1, student.getId());
						ps.setString(2, student.getName());
						ps.setString(3, student.getPasswd());
						ps.setTimestamp(4, new java.sql.Timestamp(student.getTime().getTime()));
					}
				});

	}

	public void insertStudents(final List<Student> students) {
		//final List<Student> temstudents = students;
		String sql = "insert into test_student(id, name,passwd,update_time) values(?,?,?,?)";
		int[] ii = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, students.get(i).getId());
				ps.setString(2, students.get(i).getName());
				ps.setString(3, students.get(i).getPasswd());
				ps.setTimestamp(4, new java.sql.Timestamp(students.get(i).getTime().getTime()));
			}

			public int getBatchSize() {
				return students.size();
			}
		});
		
		System.out.println("ii-size:" + ii.length);
		for (int i : ii) {
			System.out.println("ii:" + i);
		}

	}

	public int deleteStudent(int id) {
		// TODO Auto-generated method stub
		this.getJdbcTemplate().update("delete from test_student where id = ?", new Object[] { id },
				new int[] { java.sql.Types.INTEGER });
		return 0;
	}

	public void update(final Student student) {
		this.getJdbcTemplate().update("update test_student set name=?,passwd=? where id =?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, student.getName());
						ps.setString(2, student.getPasswd());
						ps.setInt(3, student.getId());
					}
				});
	}

	public List<Student> findStudents() {
		String sql = "select * from test_student";
		Object[] params = null;
		/*
		 * 如果StudentRowMapper类只使用一次，单独为其创建一个类多余，可以使用匿名类
		 * this.getJdbcTemplate().query(sql, params, new RowMapper<Student>() {
		 * 
		 * @Override public Student mapRow(ResultSet rs, int rowNum) throws
		 * SQLException { Student student = new Student(); student.setId(rs.getInt("id"));
		 * student.setStudentname(rs.getString("studentname"));
		 * student.setPassword(rs.getString("passwd")); return student; } });
		 **/
		return this.getJdbcTemplate().query(sql, new StudentRowMapper());
	}

}
