package com.ericsson.li.script;

import java.util.Map;

public class FieldOperator {
    private Map mapField;
    private JbbpParserEngine jbbpParserEngine;
    private byte[] srcData;

    public FieldOperator(JbbpParserEngine jbbpParserEngine, Map mapField, byte[] srcData) {
        this.jbbpParserEngine = jbbpParserEngine;
        this.mapField = mapField;
        this.srcData = srcData;
    }

    public void put(String key, Object value) {
        mapField.put(key, value);
    }

    public Object get(String key) {
        return mapField.get(key);
    }

    public void parseJBBP(String key) {
        jbbpParserEngine.parse(key, srcData, mapField);
    }

    public void parseJBBP(String key, byte[] b) {
        jbbpParserEngine.parse(key, b, mapField);
    }
}
