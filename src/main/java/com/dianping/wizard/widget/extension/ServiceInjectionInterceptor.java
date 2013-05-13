package com.dianping.wizard.widget.extension;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.interceptor.Interceptor;
import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-28
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class ServiceInjectionInterceptor implements Interceptor {

    private ServiceLocator locator;

    private  boolean hasLocator;

    public ServiceInjectionInterceptor() {
        String locatorClassName= Configuration.get("extension.serviceLocator",String.class);
        if(StringUtils.isEmpty(locatorClassName)){
            hasLocator=false;
            return;
        }
        try{
            locator=(ServiceLocator)Class.forName(locatorClassName).newInstance();
            hasLocator=true;
        }catch (Exception e){
            throw new WidgetException("service locator initialization failed",e);
        }

    }

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        if(hasLocator){
            invocation.getContext().put("service",locator) ;
        }
        return invocation.invoke();
    }
}
