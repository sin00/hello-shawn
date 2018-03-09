package com.ericsson.li.annotation;

public class AnnotationServiceImpl implements AnnotationService {

	@MyResource
	private StudentDao studentDao;
	
	private WorkerDao workerDao;

	@Override
	public void studentService() {
		// TODO Auto-generated method stub
		Student student = new Student(1, "Shawn Li", "passwd");
		studentDao.add(student);
		studentDao.search(student.getId());
		studentDao.delete(student.getId());
		studentDao.delete(2);
		studentDao.search(student.getId());	
	}

	@Override
	public void workerService() {
		// TODO Auto-generated method stub
		Worker worker = new Worker(2, "Litingxiang", "passwd");
		workerDao.add(worker);
		workerDao.search(worker.getId());
		workerDao.delete(worker.getId());
		workerDao.delete(1);
		workerDao.search(worker.getId());
	}
	
	@MyResource
	public void setWorkerDao(WorkerDao workerDao) {
		this.workerDao = workerDao;
	}
}
