package com.ericsson.li.ignit.client;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class MyIgniteTest extends TestCase {
private static MyIgnite myIgnite = new MyIgnite();
private static MyIgnite2 myIgnite2 = new MyIgnite2();
@Before(value = "")
public void setUp() throws Exception {
    System.out.println("setUp");
}



@Test
public void testMyIgnite() {
	System.out.println("testMyIgnite");
	myIgnite.test0();
}

@Test
public void testMyIgnite2() {
	System.out.println("testMyIgnite2");
	//myIgnite2.test();
}

}
