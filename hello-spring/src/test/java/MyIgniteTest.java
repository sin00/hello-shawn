import org.aspectj.lang.annotation.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class MyIgniteTest extends TestCase {
private static MyIgnite myIgnite = new MyIgnite();
@Before(value = "")
public void setUp() throws Exception {
    System.out.println("setUp");
}

@Test
public void testMyIgnite() {
	System.out.println("testMyIgnite");
	myIgnite.test();
}
}
