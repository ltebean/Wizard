package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WizardExeption;
import com.dianping.wizard.widget.extension.ServiceInjectionInterceptor;
import com.dianping.wizard.widget.extension.StaticModelInjectionInterceptor;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorConfig {

    private static final List<Interceptor> interceptors;

    static{
        List<String> interceptorList=Configuration.get("interceptors",List.class);
        if(CollectionUtils.isEmpty(interceptorList)){
            throw new WizardExeption("interceptors rule not found");
        }
        interceptors=new ArrayList<Interceptor>();

        try {
            for(String interceptorClass:interceptorList){
                Class clazz=Class.forName(interceptorClass);
                interceptors.add((Interceptor) clazz.newInstance());
            }
        } catch(Exception e) {
            throw new WizardExeption("interceptors initialization failed",e);
        }
    }

    public static Iterator<Interceptor> getInterceptors() {
        return interceptors.iterator();
    }

}
