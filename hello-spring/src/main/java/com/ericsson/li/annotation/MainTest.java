package com.ericsson.li.annotation;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ClassPathXMLApplicationContext classPathXMLApplicationContext = new ClassPathXMLApplicationContext(  
                "annotationInjectContext.xml");  
        
        AnnotationService annotationService = (AnnotationService)classPathXMLApplicationContext.getBean("annotationService");
        annotationService.studentService();
        System.out.println("===================================");
        annotationService.workerService();
	}

}
