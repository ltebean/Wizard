package com.dianping.wizard.widget.interceptor.core;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.script.Script;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.script.ScriptEngineFactory;
import com.dianping.wizard.repo.local.utils.ResourceList;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.WidgetRenderingException;
import com.dianping.wizard.widget.interceptor.Interceptor;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:07
 * To change this template use File | Settings | File Templates.
 */
public class BusinessInterceptor implements Interceptor {

    private final ScriptEngine engine;

    private final boolean enableDebug;

    public BusinessInterceptor() {
        enableDebug=Configuration.get("modeConfig.local.enableDebug","false",String.class).equals("true");
        if(enableDebug){
            engine = ScriptEngineFactory.getEngine("shell");
        } else{
            engine = ScriptEngineFactory.getEngine("default");
        }
    }

    @Override
    public String intercept(InvocationContext invocation) throws Exception {

        Widget widget=invocation.getWidget();
        Mode mode=widget.modes.get(invocation.getModeType());
        if(mode==null){
            throw new WidgetRenderingException("widget("+widget.name+") does not support mode:"+invocation.getModeType()+"");
        }
        if(StringUtils.isEmpty(mode.code)){
            return InvocationContext.SUCCESS;
        }
        Object result;
        try {
            if(enableDebug){
                Collection<String> paths= ResourceList.getResources(Pattern.compile(".*"+widget.name+".groovy"));
                result=engine.eval(new File(paths.iterator().next()),invocation.getContext());
            }else{
                String name= Script.generateName(widget.name, invocation.getModeType());
                result=engine.eval(new Script(name,mode.code),invocation.getContext());
            }
        } catch(Exception e) {
            throw new WidgetRenderingException(widget.name+" running error:",e.getCause());
        }

        if(InvocationContext.NONE.equals(result)){
            return InvocationContext.NONE;
        }

        if(result!=null && result instanceof Map){
            invocation.getContext().putAll((Map<String,Object>)result);
        }
        return InvocationContext.SUCCESS;
    }


}
