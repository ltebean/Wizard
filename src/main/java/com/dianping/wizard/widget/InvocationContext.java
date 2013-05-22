package com.dianping.wizard.widget;

import com.dianping.wizard.widget.interceptor.Interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:00
 * To change this template use File | Settings | File Templates.
 */
public class InvocationContext {

    public static final String SUCCESS="success";

    public static final String NONE="none";

    private final Widget widget;

    private final Map<String,Object> context;

    private String resultCode;

    private String output="";

    private String script="";

    private final String modeType;

    private final Iterator<Interceptor> interceptors;

    public InvocationContext(Widget widget, String modeType, Map<String, Object> params, Iterator<Interceptor> interceptors) {
        this.widget = widget;
        this.modeType=modeType;
        this.context = new HashMap<String, Object>();
        this.context.put("param",params);
        this.interceptors = interceptors;
    }

    public String invoke() throws Exception{
        if(interceptors.hasNext()){
            final Interceptor interceptor=interceptors.next();
            resultCode=interceptor.intercept(this);
        }
        return resultCode;
    }

    public Widget getWidget() {
        return widget;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getModeType() {
        return modeType;
    }
}
