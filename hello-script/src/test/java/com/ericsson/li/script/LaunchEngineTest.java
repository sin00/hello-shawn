package com.ericsson.li.script;

import com.ericsson.li.binary.ByteBlock;
import com.ericsson.li.binary.ByteUtil;
import org.apache.commons.jexl3.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LaunchEngineTest {
    LaunchEngine engine;

    @Before
    public void setup() {
        engine = new LaunchEngine();
    }

    @After
    public void after() {
    }

    @Test
    public void testLaunch() throws IOException {
        String jexlStarttKey = "launch-main";
        String jxltOutKey = "launch-out";

        byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, 0x00, 0x17, 0x11, 0x11, 0x00, 0x02, 0x10, 0x02, 0x00, 0x00,
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, (byte) 0xaa, (byte) 0xbb, 0x5c, 0x6e, 0x22, 0x22, 0x22, 0x22};

        Map<String, Object> dataMap  = engine.parse(jexlStarttKey, data);
        engine.convert(jxltOutKey, dataMap);
    }

    @Test
    public void testLaunch1() throws IOException {
        String jexlStarttKey = "launch-main";
        String jxltOutKey = "launch-out";

        byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, 0x00, 0x17, 0x11, 0x11, 0x00, 0x02, 0x10, 0x02, 0x00, 0x00,
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, (byte) 0xaa, (byte) 0xbb, 0x5c, 0x6e, 0x22, 0x22, 0x22, 0x22};
        jxltOutKey = "launch-out1";
        JexlContext context  = engine.parse1(jexlStarttKey, data);
        engine.convert1(jxltOutKey, context);
    }

    @Test
    public void testLaunch2() throws IOException {
        String jexlStarttKey = "launch-block";
        String jxltOutKey = "launch-out";

        byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, 0x00, 0x17, 0x11, 0x11, 0x00, 0x02, 0x10, 0x02, 0x00, 0x00,
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, (byte) 0xaa, (byte) 0xbb, 0x5c, 0x6e, 0x22, 0x22, 0x22, 0x22};
        jxltOutKey = "launch-out2";
        JexlContext context  = engine.parse2(jexlStarttKey, data);
        engine.convert1(jxltOutKey, context);
    }

    @Test
    public void testLaunch3() throws IOException {
        String jexlStarttKey = "launch-header";
        String jxltOutKey = "launch-out";

        byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, 0x00, 0x17, 0x11, 0x11, 0x00, 0x02, 0x10, 0x02, 0x00, 0x00,
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, (byte) 0xaa, (byte) 0xbb, 0x5c, 0x6e, 0x22, 0x22, 0x22, 0x22};
        jxltOutKey = "launch-out2";
        JexlContext context = new MapContext();
        context.set("SrcBlock", ByteUtil.newByteBlock(data));
        context.set("Function", new Function());
        context.set("JexlContext", context);
        context.set("Engine", engine);
        engine.parse(jexlStarttKey, context);
        engine.convert1(jxltOutKey, context);
    }

    public void printMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            System.out.println("key=" + key + " value=" + value);
        }
    }


}
