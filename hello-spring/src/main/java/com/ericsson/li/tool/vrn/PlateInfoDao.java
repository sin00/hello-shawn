package com.ericsson.li.tool.vrn;

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

/*
create table nev_plateinfo (
vc_plate_prefix      VARCHAR(30)          not null,
vc_city              VARCHAR(50)          not null,
vc_province          VARCHAR(50)          not null,
constraint PK_NEV_PLATEINFO primary key (vc_plate_prefix)
);

comment on table nev_plateinfo is
*/
public class PlateInfoDao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void create() { // test_user
		jdbcTemplate.execute(
				"create table nev_plateinfo (vc_plate_prefix VARCHAR(30) not null, vc_city VARCHAR(50) not null, vc_province  VARCHAR(50)  not null, constraint PK_NEV_PLATEINFO primary key (vc_plate_prefix))");
	}

	public void drop() { // test_user
		jdbcTemplate.execute("drop table nev_plateinfo");
	}

	public void save(PlateInfo plateInfo) {
		Assert.notNull(plateInfo, "plateInfo is not null");
		jdbcTemplate.update("insert into nev_plateinfo(vc_plate_prefix, vc_city, vc_province) values(?,?,?)",
				new Object[] { plateInfo.getPlatePrefix(), plateInfo.getCity(), plateInfo.getProvince() });
	}

	public void save2(PlateInfo plateInfo) {
		Assert.notNull(plateInfo, "plateInfo is not null");
		jdbcTemplate.update("insert into nev_plateinfo(vc_plate_prefix, vc_city, vc_province) values(?,?,?)",
				new Object[] { plateInfo.getPlatePrefix(), plateInfo.getCity(), plateInfo.getProvince() },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR, java.sql.Types.VARCHAR });
	}

	public void save3(final PlateInfo plateInfo) {
		Assert.notNull(plateInfo, "plateInfo is not null");
		jdbcTemplate.update("insert into nev_plateinfo(vc_plate_prefix, vc_city, vc_province) values(?,?,?)",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, plateInfo.getPlatePrefix());
						ps.setString(2, plateInfo.getCity());
						ps.setString(3, plateInfo.getProvince());
					}
				});

	}

	public int deleteVehicleCity(String platePrefix) {
		// TODO Auto-generated method stub
		jdbcTemplate.update("delete from nev_plateinfo where vc_plate_prefix = ?", new Object[] { platePrefix },
				new int[] { java.sql.Types.VARCHAR });
		return 0;
	}

	public void update(final PlateInfo plateInfo) {
		jdbcTemplate.update("update nev_plateinfo set vc_city=?, vc_province=? where vc_plate_prefix =?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, plateInfo.getCity());
						ps.setString(2, plateInfo.getProvince());
						ps.setString(3, plateInfo.getPlatePrefix());
					}
				});
	}

	public List<PlateInfo> query() {
		String sql = "select vc_plate_prefix, vc_city, vc_province from nev_plateinfo";
		Object[] params = null;
		return jdbcTemplate.query(sql, params, new RowMapper<PlateInfo>() {
			@Override
			public PlateInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				PlateInfo plateInfo = new PlateInfo();
				plateInfo.setPlatePrefix(rs.getString("vc_plate_prefix"));
				plateInfo.setCity(rs.getString("vc_city"));
				plateInfo.setProvince(rs.getString("vc_province"));
				return plateInfo;
			}
		});
	}

}
