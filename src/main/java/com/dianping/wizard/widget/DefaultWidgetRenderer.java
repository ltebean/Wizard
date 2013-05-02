package com.dianping.wizard.widget;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.widget.interceptor.InterceptorConfig;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
class DefaultWidgetRenderer implements WidgetRenderer {

    Logger logger= Logger.getLogger(this.getClass());

    @Override
    public String renderWidget(Widget widget, String modeType,Map<String,Object> params){
        if (widget == null) {
            throw new IllegalArgumentException("widget can not be null");
        }
        if (params == null) {
            params=new HashMap<String, Object>();
        }
        InvocationContext invocation=new InvocationContext(widget, modeType,params, InterceptorConfig.getInterceptors());
        try {
            String resultCode=invocation.invoke();
            if(resultCode==InvocationContext.SUCCESS){
                return invocation.getOutput();
            }else if(resultCode==InvocationContext.NONE){
                return "";
            }else{
                throw new WidgetException("unknown result code-"+resultCode+" returned by widget:"+widget.name);
            }
        } catch (Exception e) {
            logger.error("rendering error",e);
        }
        return "";
    }
}
