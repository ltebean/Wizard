package com.dianping.wizard.script;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.exception.WizardExeption;
import org.apache.commons.io.FileUtils;

import javax.script.*;
import java.io.File;
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

    private final ConcurrentMap<String, ScriptPack> scriptCache = new ConcurrentHashMap<String, ScriptPack>();

    public DefaultScriptEngine() {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("groovy");
    }

    public Object eval(Script script,Map<String,Object> context){
        try {
            updateCache(script);
            ScriptPack scriptPack = scriptCache.get(script.name);
            Bindings bindings = engine.createBindings();
            bindings.putAll(context);
            Object result= scriptPack.compiledScript.eval(bindings);
            return result;
        }catch (ScriptException e){
            throw new WidgetException("script running error:", e);
        }
    }

    private void updateCache(Script script) throws  ScriptException{
        ScriptPack scriptPack = scriptCache.get(script.name);
        if(scriptPack==null){
            CompiledScript compiledScript = ((Compilable) engine).compile(script.code);
            ScriptPack pack=new ScriptPack(script.code,compiledScript);
            scriptCache.putIfAbsent(script.name,pack);
            return;
        }
        if (!scriptPack.code.equals(script.code)){
            CompiledScript compiledScript = ((Compilable) engine).compile(script.code);
            scriptCache.put(script.name,new ScriptPack(script.code,compiledScript));
        }
    }

    public Object eval(File file,Map<String,Object> context){
        throw new UnsupportedOperationException();
    }

    private static class ScriptPack{
        public String code;
        public CompiledScript compiledScript;

        private ScriptPack(String code, CompiledScript compiledScript) {
            this.code = code;
            this.compiledScript = compiledScript;
        }
    }
}
