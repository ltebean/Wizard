package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.widget.InvocationContext;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 13-12-18
 * Time: PM7:47
 * To change this template use File | Settings | File Templates.
 */
public class CacheableTaskInterceptor implements Interceptor {

    @Override
    public String intercept(InvocationContext invocation) throws Exception {

        Map<String,Object> param= invocation.getParam();
        CacheableTaskProxy cacheableTaskProxy = (CacheableTaskProxy) param.get("CACHEABLE_TASK_PROXY");
        if(cacheableTaskProxy == null){
            synchronized (param) {
                if(cacheableTaskProxy == null){
                    cacheableTaskProxy = new CacheableTaskProxy();
                    param.put("CACHEABLE_TASK_PROXY", cacheableTaskProxy);
                }
            }
        }
        invocation.getContext().put("cacheable", cacheableTaskProxy);
        return invocation.invoke();
    }
}
