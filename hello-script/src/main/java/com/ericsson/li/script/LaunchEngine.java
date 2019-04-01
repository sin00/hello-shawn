package com.ericsson.li.script;

import com.ericsson.li.binary.ByteBlock;
import com.ericsson.li.binary.ByteUtil;
import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.JxltEngine.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LaunchEngine {

    Logger logger = LoggerFactory.getLogger(getClass());
    private JexlEngine jexl = null;
    private JxltEngine jxlt = null;

    private Function function = new Function();
    private JbbpParserEngine jbbpParserEngine = new JbbpParserEngine();

    private Map<String, Template> templateMap = new ConcurrentHashMap<String, Template>();
    private Map<String, JexlScript> jexlScriptMap = new ConcurrentHashMap<String, JexlScript>();

    public LaunchEngine() {
        this.jexl = new JexlBuilder().strict(false).create();
        this.jxlt = jexl.createJxltEngine();
    }

    private JexlScript addJexlScript(String key) {
        try {
            String filePath = Function.buildFilePath(key, "jexl");
            JexlScript jexlScript = jexl.createScript(Function.readFile(filePath));
            jexlScriptMap.put(key, jexlScript);
            return jexlScript;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Template addJxltTemplate(String key) {
        try {
            String filePath = Function.buildFilePath(key, "jxlt");
            Template template = jxlt.createTemplate(Function.readFile(filePath));
            templateMap.put(key, template);
            return template;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<String, Object> parse(String key, byte[] data) {
        logger.debug("------JexlScript[{}]-----", key);
        try {
            JexlScript jexlScript = jexlScriptMap.get(key);
            if (null == jexlScript) {
                jexlScript = addJexlScript(key);
                if (null == jexlScript) {
                    return null;
                }
            }
            Map<String, Object> parameters = new HashMap<String, Object>();
            FieldOperator fieldOperator = new FieldOperator(jbbpParserEngine, parameters, data);
            JexlContext context = new MapContext();
            context.set("Function", function);
            context.set("FieldOPR", fieldOperator);

            jexlScript.execute(context);
			logger.info("aa="+context.get("aa"));
            logger.info("bb="+context.get("bb"));
            logger.info("dd="+context.get("dd"));
            logger.info("kk="+context.get("kk"));
            return parameters;

        } catch (Exception e) {
            logger.error("error is occurred when parse message.", e);
        }

        return null;
    }

    public JexlContext parse1(String key, byte[] data) {
        logger.debug("------JexlScript[{}]-----", key);
        try {
            JexlScript jexlScript = jexlScriptMap.get(key);
            if (null == jexlScript) {
                jexlScript = addJexlScript(key);
                if (null == jexlScript) {
                    return null;
                }
            }
            Map<String, Object> parameters = new HashMap<String, Object>();
            FieldOperator fieldOperator = new FieldOperator(jbbpParserEngine, parameters, data);
            JexlContext context = new MapContext();
            context.set("Function", function);
            context.set("FieldOPR", fieldOperator);
            context.set("JCOPR", new JCOperator(context));

            jexlScript.execute(context);
            logger.info("aa="+context.get("aa"));
            logger.info("bb="+context.get("bb"));
            return context;

        } catch (Exception e) {
            logger.error("error is occurred when parse message.", e);
        }

        return null;
    }

    public JexlContext parse2(String key, byte[] data) {
        logger.debug("------JexlScript[{}]-----", key);
        try {
            JexlScript jexlScript = jexlScriptMap.get(key);
            if (null == jexlScript) {
                jexlScript = addJexlScript(key);
                if (null == jexlScript) {
                    return null;
                }
            }
            JexlContext context = new MapContext();
            context.set("srcBlock", ByteUtil.newByteBlock(data));
            context.set("Function", function);


            jexlScript.execute(context);
            return context;

        } catch (Exception e) {
            logger.error("error is occurred when parse message.", e);
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
            logger.error("error is occurred when parse message.", e);
        }
    }

    public String convert(String key, Map<String, Object> parameters) {
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
            JexlContext context = new MapContext();
            context.set("fields", parameters);
            StringWriter sw = new StringWriter();

            template.evaluate(context, sw);
            message = sw.toString();
            logger.debug("convert message is '{}'.", message);
        } catch (Exception e) {
            logger.error("error is occurred when convert message.", e);

        }
        return message;
    }

    public String convert1(String key, JexlContext context) {
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
            logger.error("error is occurred when convert message.", e);

        }
        return message;
    }

}
