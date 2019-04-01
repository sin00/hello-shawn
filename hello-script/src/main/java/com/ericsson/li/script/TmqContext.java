package com.ericsson.li.script;

import org.apache.commons.jexl3.JexlContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TmqContext implements JexlContext {
    private static final Logger logger = LoggerFactory.getLogger(TmqContext.class);
    private Map<String, Object> map = new ConcurrentHashMap<String, Object>();
    private JexlJxlt jexlJxlt;
    TmqContext(JexlJxlt jexlJxlt) {
        this.jexlJxlt = jexlJxlt;
    }


    @Override
    public boolean has(String name) {
        return map.containsKey(name);
    }

    @Override
    public Object get(String name) {
        return map.get(name);
    }

    @Override
    public void set(String name, Object value) {
        //logger.info("==========name[{}], value[{}]", name, value);
        if ("dd".equals(name)) {
            logger.info("avoid the name as bb");
            return;
        }
        if (null == name || value == null) {
            logger.info("==========name[{}], value[{}]", name, value);
        }
        map.put(name, value);
    }


    public void clear() {
        map.clear();
    }

    public void jexl(String jexlKey) {
        jexlJxlt.parse(jexlKey, this);
    }
    public void jxlt(String jxltKey) {
        jexlJxlt.convert(jxltKey, this);
    }

    public void haha(boolean b) {
        if (b) {
            logger.info("==========haha-true");
        } else {
            logger.info("==========haha-false");
        }
    }
}

