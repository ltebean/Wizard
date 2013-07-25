package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.script.ScriptEngineFactory;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:07
 * To change this template use File | Settings | File Templates.
 */
public class BusinessInterceptor implements Interceptor {

    private final ScriptEngine engine= ScriptEngineFactory.getEngine("default");

    @Override
    public String intercept(InvocationContext invocation) throws Exception {

        Widget widget=invocation.getWidget();
        Mode mode=widget.modes.get(invocation.getModeType());
        if(mode==null){
            throw new WidgetException("widget("+widget.name+") does not support mode:"+invocation.getModeType()+"");
        }
        if(StringUtils.isEmpty(mode.code)){
            return InvocationContext.SUCCESS;
        }
        Object result;
        try {
            long start=System.currentTimeMillis();
            result=engine.eval(mode.code,invocation.getContext());
            System.out.println("------ eval time: "+invocation.getWidget().name+(System.currentTimeMillis()-start)+"ms----");
        } catch(Exception e) {
            throw new WidgetException(widget.name+" running error:",e.getCause());
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
