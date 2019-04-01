package com.ericsson.li.script;

import com.igormaznitsa.jbbp.JBBPParser;
import com.igormaznitsa.jbbp.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JbbpParserEngine {

    Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, JBBPParser> jbbpParserMap = new ConcurrentHashMap<String, JBBPParser>();

    public void parse(String key, byte[] b, Map outMap) {
        logger.debug("------JbbpParser[{}]-----", key);
        JBBPParser parser = jbbpParserMap.get(key);
        if (null == parser) {
            parser = addParser(key);
            if (null == parser) {
                return;
            }
        }

        try {
           JBBPFieldStruct jbbpFieldStruct = parser.parse(b);
           transform(jbbpFieldStruct, outMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug("dataSize[{}], parser size[{}]", b.length, parser.getFinalStreamByteCounter());
    }

    private JBBPParser addParser(String key) {
        try {
            String filePath = Function.buildFilePath(key, "jbbp");
            String script = Function.readFile(filePath);
            JBBPParser parser = JBBPParser.prepare(script);
            jbbpParserMap.put(key, parser);
            return parser;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void transform(JBBPFieldStruct jbbpFieldStruct, Map map) {
        String pathName = jbbpFieldStruct.getFieldPath();
        if (Function.hasText(pathName)) {
            Map map1 = new HashMap();
            transform(map1, jbbpFieldStruct.getArray());
            map.put(pathName, map1);
            return;
        }
        transform(map, jbbpFieldStruct.getArray());
    }

    private void transform(Map map, JBBPAbstractField[] jbbpAbstractFields) {
        for (JBBPAbstractField jaf : jbbpAbstractFields) {
            String className = jaf.getClass().getSimpleName();
            String fieldName = jaf.getFieldName();

            if (!Function.hasText(fieldName)) {
                continue;
            }
            switch (className) {
                case "JBBPFieldByte" : {
                    map.put(fieldName, ((JBBPFieldByte)jaf).getAsInt());
                    break;
                }
                case "JBBPFieldUByte" : {
                    map.put(fieldName, ((JBBPFieldUByte)jaf).getAsInt());
                    break;
                }
                case "JBBPFieldUShort" : {
                    map.put(fieldName, ((JBBPFieldUShort)jaf).getAsInt());
                    break;
                }
                case "JBBPFieldShort" : {
                    map.put(fieldName, ((JBBPFieldShort)jaf).getAsInt());
                    break;
                }
                case "JBBPFieldInt" : {
                    map.put(fieldName, ((JBBPFieldInt)jaf).getAsInt());
                    break;
                }
                case "JBBPFieldBit" : {
                    map.put(fieldName, ((JBBPFieldBit)jaf).getAsInt());
                    break;
                }
                case "JBBPFieldArrayByte" : {
                    map.put(fieldName, ((JBBPFieldArrayByte)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayUByte" : {
                    map.put(fieldName, ((JBBPFieldArrayUByte)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayBit" : {
                    map.put(fieldName, ((JBBPFieldArrayBit)jaf).getArray());
                    break;
                }
                case "JBBPFieldLong" : {
                    map.put(fieldName, ((JBBPFieldLong)jaf).getAsLong());
                    break;
                }
                case "JBBPFieldStruct" : {
                    transform((JBBPFieldStruct)jaf, map);
                    break;
                }
                case "JBBPFieldArrayStruct" : {
                    JBBPFieldStruct[] jbbpFieldStructs = ((JBBPFieldArrayStruct)jaf).getArray();
                    HashMap [] maps = new HashMap[jbbpFieldStructs.length];
                    for (int i = 0; i < jbbpFieldStructs.length; i++) {
                        maps[i] = new HashMap();
                        transform(jbbpFieldStructs[i], maps[i]);
                    }

                    map.put(fieldName, maps);
                    break;
                }
                case "JBBPFieldString" : {
                    map.put(fieldName, ((JBBPFieldString)jaf).getAsString());
                    break;
                }
                case "JBBPFieldBoolean" : {
                    map.put(fieldName, ((JBBPFieldBoolean)jaf).getAsBool());
                    break;
                }
                case "JBBPFieldDouble" : {
                    map.put(fieldName, ((JBBPFieldDouble)jaf).getAsDouble());
                    break;
                }
                case "JBBPFieldFloat" : {
                    map.put(fieldName, ((JBBPFieldFloat)jaf).getAsFloat());
                    break;
                }
                case "JBBPFieldArrayShort" : {
                    map.put(fieldName, ((JBBPFieldArrayShort)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayUShort" : {
                    map.put(fieldName, ((JBBPFieldArrayUShort)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayInt" : {
                    map.put(fieldName, ((JBBPFieldArrayInt)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayLong" : {
                    map.put(fieldName, ((JBBPFieldArrayLong)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayString" : {
                    map.put(fieldName, ((JBBPFieldArrayString)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayBoolean" : {
                    map.put(fieldName, ((JBBPFieldArrayBoolean)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayDouble" : {
                    map.put(fieldName, ((JBBPFieldArrayDouble)jaf).getArray());
                    break;
                }
                case "JBBPFieldArrayFloat" : {
                    map.put(fieldName, ((JBBPFieldArrayFloat)jaf).getArray());
                    break;
                }
            }
            logger.info("key=" + fieldName + " value=" + map.get(fieldName));
        }
    }
}
