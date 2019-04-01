package com.ericsson.li.groovy;

import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;

import javax.script.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Groovy {
    public Object runGroovyScriptByFile(String[] filepath, String filename, Map<String, Object> params) {
    	System.out.println("----------runGroovyScriptByFile---------");
        if (filepath == null || filepath.length == 0)
            filepath = new String[] { "grovvy\\" };

        try {
            // String[]{".\\src\\main\\java\\com\\senyint\\util\\"}
            GroovyScriptEngine engine = new GroovyScriptEngine(filepath);
            return engine.run(filename, new Binding(params));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public Object runGroovyScriptByFile(String[] filepath, String filename, String funname, Object[] params) {
    	System.out.println("----------runGroovyScriptByFile---------");
    	Object o = null;
        if (filepath == null || filepath.length == 0)
            filepath = new String[] { "src/main/groovy" };
        try {
            // String[]{".\\src\\main\\java\\com\\senyint\\util\\"}
            GroovyScriptEngine engine = new GroovyScriptEngine(filepath);
            Script script = engine.createScript(filename, new Binding());
            o = script.invokeMethod(funname, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(o);
        return o;
    }


    public Object runGroovyScript(String script, Map<String, Object> params) {
    	System.out.println("----------runGroovyScript map---------");
    	Object o = null;
        if (script == null || "".equals(script))
            throw new RuntimeException("script is null in runGroovyScript");

        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            Bindings bindings = engine.createBindings();
            bindings.putAll(params);
            
            o = engine.eval(script, bindings);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(o);
        return o;
    }


    public Object runGroovyScript(String script, String funName, Object[] params) {
    	System.out.println("----------runGroovyScript argv[]---------");
    	Object o = null;
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            // String HelloLanguage = "def hello(language) {return \"Hello
            // $language\"}";
            engine.eval(script);
            Invocable inv = (Invocable) engine;
            o = inv.invokeFunction(funName, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(o);
        return o;
    }

    public void getScriptEngineFactoryList() {
    	System.out.println("----------getScriptEngineFactoryList---------");
        ScriptEngineManager manager = new ScriptEngineManager();

        List<ScriptEngineFactory> factories = manager.getEngineFactories();

        for (ScriptEngineFactory factory : factories) {

            System.out.printf(
                    "Name: %s%n" + "Version: %s%n" + "Language name: %s%n" + "Language version: %s%n"
                            + "Extensions: %s%n" + "Mime types: %s%n" + "Names: %s%n",
                    factory.getEngineName(), factory.getEngineVersion(), factory.getLanguageName(),
                    factory.getLanguageVersion(), factory.getExtensions(), factory.getMimeTypes(), factory.getNames());


            ScriptEngine engine = factory.getScriptEngine();
            System.out.println(engine);
        }
    }

    public static void main(String[] args) {
        Groovy groovy = new Groovy();
        groovy.getScriptEngineFactoryList();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("language", "groovy test");
        groovy.runGroovyScript("return \"Hello $language\"", params);
        String script = "def hello(param1,param2) {return \"the params is $param1 and $param2\"}";
        groovy.runGroovyScript(script, "hello", new String[] { "param1", "param2" });
        groovy.runGroovyScriptByFile(null, "hello.groovy", "hello", new String[] { "param3", "param4" });

    }
}

