package com.dianping.wizard.script.module;

import com.dianping.wizard.repo.WidgetRepoFactory;
import com.dianping.wizard.script.Script;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.script.ScriptEngineFactory;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.WidgetRendererFactory;
import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ltebean
 */
public class Wizard {

    private ConcurrentMap<String,Object> modules=new ConcurrentHashMap<String, Object>();

    public void define(String name,Closure closure){
        modules.put(name,closure.call());
    }

    public void require(String moduleName,Closure callback){
        Object module=modules.get(moduleName);
        if(module==null){
            loadModule(moduleName);
            callback.call(modules.get(moduleName));
        }else{
            callback.call(module);
        }
    }

    public void require(List<String> moduleNames,Closure callback){
        List<Object> args= new ArrayList<Object>();
        for (String moduleName : moduleNames) {
            Object module=modules.get(moduleName);
            if(module==null){
                loadModule(moduleName);
                args.add(modules.get(moduleName));
            }else{
                args.add(module);
            }
        }
        callback.call(args);
    }

    private void loadModule(String moduleName){
        WidgetRendererFactory.getRenderer("default").render("mod@"+moduleName,new HashMap<String, Object>());
    }

}
