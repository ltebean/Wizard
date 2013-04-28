package com.dianping.wizard.script;

import com.dianping.wizard.exception.WidgetException;

import javax.script.ScriptEngineManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public class ScriptEngine {
    private static final ScriptEngine ourInstance = new ScriptEngine();

    private javax.script.ScriptEngine engine;

    public static ScriptEngine getInstance() {
        return ourInstance;
    }

    private ScriptEngine() {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("groovy");
    }

    public Object eval(Map<String,Object> context,String code){
        try {
            for(Map.Entry<String,Object> entry : context.entrySet()) {
                engine.put(entry.getKey(),entry.getValue());
            }
            engine.eval(code);
            Object result= engine.get("result");
            return result;
        }catch (Exception e){
            throw new WidgetException("script error:",e);
        }
    }

}
