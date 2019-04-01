package com.ericsson.li.type;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


class BaseTypeValueOf {
    public BaseTypeValueOf() {
        try {
            Boolean aBoolean = new Boolean(true);
            Byte aByte = new Byte((byte) 0);
            Short aShort = new Short((short) 0);
            Integer integer = new Integer(0);
            Long aLong = new Long(0);
            Float aFloat = new Float(0);
            Double aDouble = new Double(0);
            String string = "";
            objectMethodMap.put(Boolean.class, new ObjectMethod(aBoolean, aBoolean.getClass().getMethod("valueOf", new Class[]{String.class})));
            objectMethodMap.put(Byte.class, new ObjectMethod(aByte, aByte.getClass().getMethod("valueOf", new Class[]{String.class})));
            objectMethodMap.put(Short.class, new ObjectMethod(aShort, aShort.getClass().getMethod("valueOf", new Class[]{String.class})));
            objectMethodMap.put(Integer.class, new ObjectMethod(integer, integer.getClass().getMethod("valueOf", new Class[]{String.class})));
            objectMethodMap.put(Long.class, new ObjectMethod(aLong, aLong.getClass().getMethod("valueOf", new Class[]{String.class})));
            objectMethodMap.put(Float.class, new ObjectMethod(aFloat, aFloat.getClass().getMethod("valueOf", new Class[]{String.class})));
            objectMethodMap.put(Double.class, new ObjectMethod(aDouble, aDouble.getClass().getMethod("valueOf", new Class[]{String.class})));
            objectMethodMap.put(String.class, new ObjectMethod(string, string.getClass().getMethod("valueOf", new Class[]{Object.class})));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Map<Class, ObjectMethod> objectMethodMap = new HashMap<>();

    public ObjectMethod getObjectMethod(Class clazz) {
        return objectMethodMap.get(clazz);
    }

}

class ObjectMethod {
    private Object object;
    private Method method;

    public ObjectMethod(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public Object invoke(Object args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object, args);
    }
}

public class BeanUtil1 {
    private static BaseTypeValueOf baseTypeValueOf = new BaseTypeValueOf();
    private static Map<Class, Map<String, ObjectMethod>> mapBeanAttibute = new ConcurrentHashMap<Class, Map<String, ObjectMethod>>();

    public static void registerBean(Class beanClazz) {
        Map<String, ObjectMethod> map = new HashMap<String, ObjectMethod>();
        Field[] fields = beanClazz.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println("类型" + fields[i].getType());
            System.out.println("名称=" + fields[i].getName());
            ObjectMethod objectMethod = baseTypeValueOf.getObjectMethod(fields[i].getType());
            if (null != objectMethod) {
                map.put(fields[i].getName(), objectMethod);
            }
        }
        mapBeanAttibute.put(beanClazz, map);
    }

    public static Object castAttributeValue(Class beanClazz, String attribute, String attrValue) {
        if (null == beanClazz || null == attribute || null == attrValue) {
            return null;
        }
        Map<String, ObjectMethod> map = mapBeanAttibute.get(beanClazz);
        if (null == map) {
            return null;
        }
        ObjectMethod objectMethod = map.get(attribute);
        if (null == objectMethod) {
            return null;
        }
        Object o = null;
        try {
            o = objectMethod.invoke(attrValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void main(String[] args) {
        Class clazz = Chinese.class;
        System.out.println(clazz.getCanonicalName());
//        System.out.println(clazz.getComponentType().getCanonicalName());
        System.out.println(clazz.getName());
        System.out.println(clazz.getSuperclass().getName());
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getTypeName());

        if (clazz.getSuperclass() == Person.class) {
            System.out.println("clazz.getSuperclass() == Person.class");
        }

        if (Integer.class.getSuperclass() == Number.class) {
            System.out.println("Integer.class.getSuperclass() == Number.class");
        }

        if (Integer.class.getSuperclass().equals(Number.class)) {
            System.out.println("Integer.class.getSuperclass().equals(Number.class)");
            // Number.class.
        }

        if (Object.class.equals(Number.class)) {
            System.out.println("Object.class.equals(Number.class)");
        }

        System.out.println(Boolean.valueOf("true"));
        System.out.println(Boolean.valueOf("false"));
        System.out.println(Boolean.valueOf("123"));

        try {
            Method method = Integer.class.getMethod("valueOf", new Class[]{String.class});
            System.out.println(method.invoke(new Integer(0), "555"));
            method = String.class.getMethod("valueOf", new Class[]{Object.class});
            System.out.println(method.invoke(new String("dddd"), 66666));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(Number.class.);

        BeanUtil1.registerBean(Person.class);
        System.out.println("name:" + BeanUtil1.castAttributeValue(Person.class, "name", "taofenboy") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "name", "taofenboy").getClass().getName());
        System.out.println("age:" + BeanUtil1.castAttributeValue(Person.class, "age", "20") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "age", "20").getClass().getName());
        System.out.println("character:" + BeanUtil1.castAttributeValue(Person.class, "character", "A") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "character", "A").getClass().getName());
        System.out.println("high:" + BeanUtil1.castAttributeValue(Person.class, "high", "144.4") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "high", "144.4").getClass().getName());
        System.out.println("price:" + BeanUtil1.castAttributeValue(Person.class, "price", "12345.78") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "price", "12345.78").getClass().getName());
        System.out.println("good:" + BeanUtil1.castAttributeValue(Person.class, "good", "false") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "good", "false").getClass().getName());
        System.out.println("hands:" + BeanUtil1.castAttributeValue(Person.class, "hands", "11") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "hands", "11").getClass().getName());
        System.out.println("feets:" + BeanUtil1.castAttributeValue(Person.class, "feets", "9") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "feets", "9").getClass().getName());
        System.out.println("eye:" + BeanUtil1.castAttributeValue(Person.class, "eye", "3") + "====>type:" + BeanUtil1.castAttributeValue(Person.class, "eye", "3").getClass().getName());
    }


}


interface Animal {
    public void eat();
}

class Person {

    protected String name;
    protected Integer age;
    protected Character character;
    protected Float high;
    protected Double price;
    protected Boolean good;
    protected Short hands;
    protected Long feets;
    protected Byte eye;
    protected Date date;

    public Person() {
    }
}

class Chinese extends Person implements Animal {
    public void eat() {
        System.out.println(name + " eating.");
    }
}