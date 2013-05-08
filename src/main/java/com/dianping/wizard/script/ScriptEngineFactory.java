package com.dianping.wizard.script;

import com.dianping.wizard.exception.WizardExeption;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */
public class ScriptEngineFactory {

    private static Map<String,ScriptEngine> engines;

    static{
        engines=new HashMap<String, ScriptEngine>();
        engines.put("default",new DefaultScriptEngine());
    }

    public static ScriptEngine getEngine(String name){
        ScriptEngine engine=engines.get(name);
        if(engine==null){
            throw new WizardExeption("script engine not found: "+name);
        }
        return engine;
    }

}
