package com.dianping.wizard.widget;

import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.widget.interceptor.InterceptorConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
public class DefaultWidgetManager implements WidgetManager {

    @Override
    public String renderWidget(Widget widget, Widget.ModeType modeType,Map<String,Object> params){
        if (widget == null) {
            throw new IllegalArgumentException("widget can not be null");
        }
        InvocationContext invocation=new InvocationContext(widget, modeType,params, InterceptorConfig.getInterceptors());
        try {
            invocation.invoke();
            return invocation.getOutput();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "";
    }
}
