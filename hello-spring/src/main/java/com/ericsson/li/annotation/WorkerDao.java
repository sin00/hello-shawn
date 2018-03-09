package com.ericsson.li.annotation;

public interface WorkerDao {
	public void add(Worker worker);
	public void delete(int id);
	public void search(int id);
}
