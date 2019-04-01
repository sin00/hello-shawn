package com.ericsson.li.script;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.JxltEngine.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JexlJxlt {

    Logger logger = LoggerFactory.getLogger(getClass());
    private JexlEngine jexlEngine = null;
    private JxltEngine jxltEngine = null;

    private Map<String, Template> templateMap = new ConcurrentHashMap<String, Template>();
    private Map<String, JexlScript> jexlScriptMap = new ConcurrentHashMap<String, JexlScript>();

    public JexlJxlt(JexlEngine jexlEngine, JxltEngine jxltEngine) {
        this.jexlEngine = jexlEngine;
        this.jxltEngine = jxltEngine;
        //this.jexl = new JexlBuilder().strict(false).create();
        //this.jxlt = jexl.createJxltEngine();
    }

    synchronized private JexlScript addJexlScript(String key) {
        try {
            JexlScript jexlScript = jexlScriptMap.get(key);
            if (jexlScript != null) {
                return jexlScript;
            }
            String filePath = Function.buildFilePath(key, "jexl");
            jexlScript = jexlEngine.createScript(Function.readFile(filePath));
            jexlScriptMap.put(key, jexlScript);
            return jexlScript;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    synchronized private Template addJxltTemplate(String key) {
        try {
            Template template = templateMap.get(key);
            if (template != null) {
                return template;
            }
            String filePath = Function.buildFilePath(key, "jxlt");
            template = jxltEngine.createTemplate(Function.readFile(filePath));
            templateMap.put(key, template);
            return template;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parse(String key, JexlContext context) {
        logger.debug("------JexlScript[{}]-----", key);
        try {
            JexlScript jexlScript = jexlScriptMap.get(key);
            if (null == jexlScript) {
                jexlScript = addJexlScript(key);
                if (null == jexlScript) {
                    return;
                }
            }

            jexlScript.execute(context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String convert(String key, JexlContext context) {
        logger.debug("------JxltTemplate[{}]-----", key);
        String message = null;
        try {
            Template template = templateMap.get(key);
            if (null == template) {
                template = addJxltTemplate(key);
                if (null == template) {
                    return null;
                }
            }
            StringWriter sw = new StringWriter();

            template.evaluate(context, sw);
            message = sw.toString();
            logger.debug("convert message is '{}'.", message);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return message;
    }

}
