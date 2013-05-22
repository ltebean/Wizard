package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.widget.concurrent.ConcurrentRenderLayoutInterceptor;
import com.dianping.wizard.widget.extensions.ExtensionsInjectionInterceptor;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:38
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorConfig {

    private static final Map<String,List<Interceptor>> interceptors;

    static{
        interceptors=new HashMap<String, List<Interceptor>>();

        Interceptor debug=new DebuggingInterceptor();
        Interceptor exception=new ExceptionInterceptor();
        Interceptor merge=new MergeInterceptor();
        Interceptor layout=new RenderLayoutInterceptor();
        Interceptor concurrentLayout=new ConcurrentRenderLayoutInterceptor();
        Interceptor extensions=new ExtensionsInjectionInterceptor();
        Interceptor business=new BusinessInterceptor();

        List<Interceptor> defaultStack= new ArrayList<Interceptor>();
        defaultStack.add(debug);
        defaultStack.add(exception);
        defaultStack.add(merge);
        defaultStack.add(layout);
        defaultStack.add(extensions);
        defaultStack.add(business);

        List<Interceptor> concurrentStack= new ArrayList<Interceptor>();
        concurrentStack.add(exception);
        concurrentStack.add(merge);
        concurrentStack.add(concurrentLayout);
        concurrentStack.add(extensions);
        concurrentStack.add(business);

        interceptors.put("default",defaultStack);
        interceptors.put("concurrent",concurrentStack);
    }

    public static Iterator<Interceptor> getInterceptors(String name) {
        return interceptors.get(name).iterator();
    }

}
