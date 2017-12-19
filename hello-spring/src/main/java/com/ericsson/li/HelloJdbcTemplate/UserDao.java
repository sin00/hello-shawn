package com.ericsson.li.HelloJdbcTemplate;

import java.util.List;

public interface UserDao {
	public List<VehicleCity> findUsers();
	public void create();
	public void drop();
	public void save(VehicleCity user);
	public void save2(VehicleCity user);
	public void save3(final VehicleCity user);
	public int deleteUser(int id);
	public void update(final VehicleCity user);
}
