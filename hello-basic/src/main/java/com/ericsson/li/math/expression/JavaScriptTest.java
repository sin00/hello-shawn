package com.ericsson.li.math.expression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

public class JavaScriptTest {
    public static void main(String[] args) {
        // String exp = "2 + (7-5) * 3.14159 * x + sin(0)";

        String sbt = "(B+D-(A-C)*A)/F";
        List<MapJ> all = new ArrayList<MapJ>();
        all.add(new MapJ("A", "2"));
        all.add(new MapJ("B", "3"));
        all.add(new MapJ("C", "4"));
        all.add(new MapJ("D", "5"));
        all.add(new MapJ("F", "24"));
        JavaScript js = new JavaScript();
        Double d = js.getMathValue(all, sbt);
        if (d == null) {
            System.out.println("                 无法计算这个表达式");
        } else {
            System.out.println(d * 100 + "%");
        }
    }
}

class MapJ {
    private String key;//替换的编号
    private String value;//值

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MapJ(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public MapJ() {
        super();
    }

}

class JavaScript {
    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("JavaScript");

    public Double getMathValue(List<MapJ> map, String option) {
        double d = 0;
        try {
            for (int i = 0; i < map.size(); i++) {
                MapJ mapj = map.get(i);
                option = option.replaceAll(mapj.getKey(), mapj.getValue());
            }
            Object o = engine.eval(option);
            d = Double.parseDouble(o.toString());
        } catch (ScriptException e) {
            System.out.println("无法识别表达式");
            return null;
        }
        return d;
    }
}
