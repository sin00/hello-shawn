package com.ericsson.li.type;


import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtil {
    private static  BaseCastFunction baseCastFunction = new BaseCastFunction();
    private static Map<String, CastFunction> functionMap = new ConcurrentHashMap<>();
    private static Map<Class, Object> registerClassMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        //testClassCastAttribute();
        testConvert();
    }

    public static Object castClassAttributeValue(Class beanClazz, String attribute, String attrValue) {
        if (null == beanClazz || null == attribute || null == attrValue) {
            return null;
        }
        if (!registerClassMap.containsKey(beanClazz)) {
            registerClass(beanClazz);
        }

        CastFunction castFunction = functionMap.get(getClassAttributeName(beanClazz.getName(), attribute));
        if (null != castFunction) {
            return castFunction.op(attrValue);
        }

        return null;
    }

    private static String getClassAttributeName(String beanTypeName, String beanAttributeName) {
        String key =  new StringBuilder(beanTypeName).append(".").append(beanAttributeName).toString();
        //System.out.println("key:" + key);
        return  key;
    }


    private static void registerClass(Class beanClazz) {
        Field[] fields = beanClazz.getDeclaredFields();
        System.out.println("REGISTER bean: " + beanClazz.getName());
        registerClassMap.put(beanClazz, "");
        for (Field field : fields) {
            CastFunction castFunction = baseCastFunction.getCastFunction(field.getType());
            if (null != castFunction) {
                functionMap.put(getClassAttributeName(beanClazz.getName(), field.getName()), castFunction);
                System.out.println("REGISTER attribute/attribute-type:" + field.getName()+ "/" + field.getType().getName());
            } else {
                System.out.println("IGNORE attribute/attribute-type:" + field.getName()+ "/" + field.getType().getName());
            }
        }
    }

    public static void testClassCastAttribute() {
        System.out.println("name:" + BeanUtil.castClassAttributeValue(Person.class, "name", "taofenboy") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "name", "taofenboy").getClass().getName());
        System.out.println("age:" + BeanUtil.castClassAttributeValue(Person.class, "age", "20") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "age", "20").getClass().getName());
        System.out.println("character:" + BeanUtil.castClassAttributeValue(Person.class, "character", "A") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "character", "A").getClass().getName());
        System.out.println("high:" + BeanUtil.castClassAttributeValue(Person.class, "high", "144.4") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "high", "144.4").getClass().getName());
        System.out.println("price:" + BeanUtil.castClassAttributeValue(Person.class, "price", "12345.78") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "price", "12345.78").getClass().getName());
        System.out.println("good:" + BeanUtil.castClassAttributeValue(Person.class, "good", "false") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "good", "false").getClass().getName());
        System.out.println("hands:" + BeanUtil.castClassAttributeValue(Person.class, "hands", "11") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "hands", "11").getClass().getName());
        System.out.println("feets:" + BeanUtil.castClassAttributeValue(Person.class, "feets", "9") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "feets", "9").getClass().getName());
        System.out.println("eye:" + BeanUtil.castClassAttributeValue(Person.class, "eye", "3") + "====>type:" + BeanUtil.castClassAttributeValue(Person.class, "eye", "3").getClass().getName());
    }

    public static void testConvert() {
        Integer[] ids = (Integer[]) ConvertUtils.convert(",1, 2, 3,  45,o".split(","), Integer.class);
        System.out.println("ids size is " + ids.length);
        for (Integer id : ids) {
            System.out.println("id" + id);
        }
    }
}


interface CastFunction {
    Object op(String attrValue);
}

class BaseCastFunction {
    private Map<Class, CastFunction> castFunctionHashMap = new HashMap<>();
    BaseCastFunction() {
        castFunctionHashMap.put(Boolean.class, s->Boolean.valueOf(s));
        castFunctionHashMap.put(Byte.class, s->Byte.valueOf(s));
        castFunctionHashMap.put(Character.class, s->s.charAt(0));
        castFunctionHashMap.put(Short.class, s->Short.valueOf(s));
        castFunctionHashMap.put(Integer.class, s->Integer.valueOf(s));
        castFunctionHashMap.put(Long.class, s->Long.valueOf(s));
        castFunctionHashMap.put(Float.class, s->Float.valueOf(s));
        castFunctionHashMap.put(Double.class, s->Double.valueOf(s));
        castFunctionHashMap.put(String.class, s->s);
    }

    CastFunction getCastFunction(Class clazz) {
        return castFunctionHashMap.get(clazz);
    }
}

