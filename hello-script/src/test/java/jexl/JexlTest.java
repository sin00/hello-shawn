package jexl;

import com.ericsson.li.script.LaunchEngine;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.MapContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class JexlTest {
    private JexlEngine JEXL;

    @Before
    public void setup() {
        JEXL = new JexlBuilder().strict(false).create();
    }

    @After
    public void after() {
    }

    @Test
    public void testBigdOp() throws Exception {
        BigDecimal sevendot475 = new BigDecimal("7.475");
        BigDecimal SO = new BigDecimal("325");
        JexlContext jc = new MapContext();
        jc.set("SO.aa", SO);

        String expr = "2.3*SO.aa/100";

        Object evaluate = JEXL.createExpression(expr).evaluate(jc);
        System.out.println("evaluate:" + evaluate);
        Assert.assertEquals(sevendot475, (BigDecimal) evaluate);
    }

    @Test
    public void aabb() throws Exception {
        BigDecimal sevendot475 = new BigDecimal("7.475");
        BigDecimal SO = new BigDecimal("325");
        JexlContext jc = new MapContext();
        jc.set("hello.aa", 10);
        jc.set("hello.bb", 20);

        String expr = "(3*hello.aa-hello.bb)*3.5";

        Object evaluate = JEXL.createExpression(expr).evaluate(jc);
        System.out.println("evaluate:" + evaluate);
    }

    @Test
    public void aa() throws Exception {
        BigDecimal sevendot475 = new BigDecimal("7.475");
        BigDecimal SO = new BigDecimal("325");
        JexlContext jc = new MapContext();
        jc.set("hello.aa", 10);
        jc.set("hello.bb", 20);

        String expr = "hello.aa";

        Object evaluate = JEXL.createExpression(expr).evaluate(jc);
        System.out.println("evaluate:" + evaluate);
    }

    @Test
    public void bb() throws Exception {
        BigDecimal sevendot475 = new BigDecimal("7.475");
        BigDecimal SO = new BigDecimal("325");
        JexlContext jc = new MapContext();
        jc.set("hello.aa", 10);
        jc.set("hello.bb", 20);

        String expr = "10";

        Object evaluate = JEXL.createExpression(expr).evaluate(jc);
        System.out.println("evaluate:" + evaluate);
    }

}
