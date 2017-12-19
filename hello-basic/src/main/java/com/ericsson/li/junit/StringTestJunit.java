package com.ericsson.li.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ericsson.li.type.StringTest;

public class StringTestJunit {
	@Before
	public void befor() {
		System.out.println("befor");
	}
	
	@Test
	public void testDelete() {
		StringTest.testDelete();
		Assert.assertEquals(1, 2);
	}
	
	@Test
	public void testContain() {
		StringTest.testContain();
	}
	
	@After
	public void after() {
		System.out.println("after");
	}

	//testContain();
	//testByte("ABCDE12345FGH");
	//testCut();
	//testReplace();
	//testChar();
	//testNewStringTest();
	//commontest();
	//testSubstring();
	//strTest();
	//strTest2("");
}
