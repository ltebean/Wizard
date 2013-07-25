package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.widget.concurrent.ConcurrentLayoutInterceptor;
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

    private final Map<String, Interceptor> factory = new HashMap<String, Interceptor>();


    private final Map<String, List<Interceptor>> stacks = new HashMap<String, List<Interceptor>>();

    private static InterceptorConfig instance=new InterceptorConfig();


    private InterceptorConfig()   {
        //initialize built-in interceptors
        factory.put("exception", new ExceptionInterceptor());
        factory.put("merge", new MergeInterceptor());
        factory.put("layout", new LayoutInterceptor());
        factory.put("concurrentLayout", new ConcurrentLayoutInterceptor());
        factory.put("extensions", new ExtensionsInjectionInterceptor());
        factory.put("business", new BusinessInterceptor());

        //initialize user-defined interceptors
        Map<String, String> userDefinedFactory = Configuration.get("interceptors.factory", new HashMap<String, String>(), Map.class);
        if (userDefinedFactory.size() > 0) {
            for (String interceptorName : userDefinedFactory.keySet()) {
                try {
                    Interceptor interceptor = (Interceptor) Class.forName(userDefinedFactory.get(interceptorName)).newInstance();
                    factory.put(interceptorName, interceptor);
                } catch (Exception e) {
                    throw new WidgetException("faild to initialize interceptor: " + interceptorName, e);
                }

            }
        }
        //initialize stack config
        Map<String, String> stacksRules = Configuration.get("interceptors.stack", new HashMap<String, String>(), Map.class);
        if (stacksRules.size() == 0) {
            throw new WidgetException("stack rule not found");
        }
        for(String stackName:stacksRules.keySet()){
            List<Interceptor> interceptors=new ArrayList<Interceptor>();
            for(String interceptorName:stacksRules.get(stackName).split("\\|")){
                Interceptor interceptor=factory.get(interceptorName);
                if (interceptor == null) {
                    throw new WidgetException("interceptor not found: " + interceptorName);
                }
                interceptors.add(interceptor);
            }
            stacks.put(stackName,interceptors);
        }


    }

    public static InterceptorConfig getInstance(){
        return instance;
    }
    public  Iterator<Interceptor> getInterceptors(String name) {
        return stacks.get(name).iterator();
    }

}
