package com.dianping.wizard.script;

import com.dianping.wizard.exception.WidgetException;

import javax.script.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public class DefaultScriptEngine implements ScriptEngine{

    private final javax.script.ScriptEngine engine;

    private final ConcurrentMap<String, CompiledScript> compiledScripts = new ConcurrentHashMap<String, CompiledScript>();

    public DefaultScriptEngine() {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("groovy");
    }

    public Object eval(String code,Map<String,Object> context){

        try {
            CompiledScript script = compiledScripts.get(code);
            if(script==null){
                script = ((Compilable) engine).compile(code);
                compiledScripts.putIfAbsent(code, script);
            }
            Bindings bindings = engine.createBindings();
            bindings.putAll(context);
            Object result= script.eval(bindings);
            return result;
        }catch (Exception e){
            throw new WidgetException("script running error:", e.getCause());
        }
    }

}
