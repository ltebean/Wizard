package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.widget.CacheTaskProxy;
import com.dianping.wizard.widget.InvocationContext;
import groovy.lang.GroovyClassLoader;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 13-12-18
 * Time: PM7:47
 * To change this template use File | Settings | File Templates.
 */
public class CacheTaskInterceptor implements Interceptor {


    @Override
    public String intercept(InvocationContext invocation) throws Exception {

        HashMap<String,Object> param=  (HashMap<String,Object>)invocation.getContext().get("param");
        CacheTaskProxy cacheTaskProxy = (CacheTaskProxy) param.get("CACHE_TASK_PROXY");
        if(cacheTaskProxy == null){
            synchronized (param) {
                if(cacheTaskProxy == null){
                    cacheTaskProxy = new CacheTaskProxy();
                    param.put("CACHE_TASK_PROXY", cacheTaskProxy);
                }
            }
        }
        invocation.getContext().put("cacheTaskProxy", cacheTaskProxy);

        return invocation.invoke();
    }





}
