package com.ericsson.li.script;

import com.ericsson.li.binary.ByteBlock;
import com.ericsson.li.binary.ByteFrame;
import com.ericsson.li.binary.ByteUtil;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JxltEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class JexlJxltTest {
    private JexlJxlt jexlJxlt;
    @Before
    public void setup() {
        JexlEngine jexlEngine = new JexlBuilder().strict(false).create();
        JxltEngine jxltEngine = jexlEngine.createJxltEngine();
        jexlJxlt = new JexlJxlt(jexlEngine, jxltEngine);
    }

    @After
    public void after() {
    }
    @Test
    public void testAA() throws IOException {
        String jexlStartKey = "aa-header";
        String jxltOutKey = "aa-out";

        byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, 0x00, 0x17, 0x11, 0x11, 0x00, 0x02, 0x10, 0x02, 0x00, 0x00,
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, (byte) 0xaa, (byte) 0xbb, 0x5c, 0x6e, 48, 49, 50, 51, 52, 53, 54, 55};


        JexlContext context = new TmqContext(jexlJxlt);
        context.set("SrcBytes", data);
        context.set("ByteUtil", ByteUtil.getInstance());
        context.set("Function", new Function());

        jexlJxlt.parse(jexlStartKey, context);
        //jexlJxlt.convert(jxltOutKey, context);
    }
}
