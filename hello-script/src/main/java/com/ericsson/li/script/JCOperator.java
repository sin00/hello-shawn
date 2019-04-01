package com.ericsson.li.script;

import org.apache.commons.jexl3.JexlContext;

import java.util.Map;

public class JCOperator {
    private JexlContext context;

    public JCOperator(JexlContext context) {
        this.context = context;
    }

    public void put(String key, Object value) {
        context.set(key, value);
    }

    public Object get(String key) {
        return context.get(key);
    }

}
