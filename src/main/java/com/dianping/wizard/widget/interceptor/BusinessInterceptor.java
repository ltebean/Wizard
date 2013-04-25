package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:07
 * To change this template use File | Settings | File Templates.
 */
public class BusinessInterceptor implements Interceptor {

    private ScriptEngine engine= ScriptEngine.getInstance();

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        Widget widget=invocation.getWidget();
        Map<String,Object> context=invocation.getContext();
        context.put("config",widget.getConfig());
        Mode mode=widget.getModes().get(invocation.getModeType().value);
        if(mode==null){
            throw new WidgetException("mode not found:"+invocation.getModeType());
        }
        Map<String,Object> result= engine.eval(context,mode.getCode());
        invocation.getContext().putAll(result);
        return InvocationContext.SUCCESS;
    }
}
