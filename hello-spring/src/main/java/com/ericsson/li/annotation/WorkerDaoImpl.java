package com.ericsson.li.annotation;

import java.util.HashMap;
import java.util.Map;

public class WorkerDaoImpl implements WorkerDao {
	private Map<Integer, Worker> mapWorker = new HashMap<Integer, Worker>();
	@Override
	public void add(Worker worker) {
		// TODO Auto-generated method stub
		System.out.println("ADD:" + worker.toString());
		mapWorker.put(worker.getId(), worker);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Worker worker = mapWorker.get(id);
		if (worker != null) {
			System.out.println("DELETE:" + worker.toString());
			mapWorker.remove(id);
		} else {
			System.out.println("DELETE: could not find Worker by id " + id);
		}
	}

	@Override
	public void search(int id) {
		// TODO Auto-generated method stub
		Worker worker = mapWorker.get(id);
		if (worker != null) {
			System.out.println("SELECT:" + worker.toString());
		} else {
			System.out.println("SELECT: could not find Worker by id " + id);
		}
	
	}
	
}
