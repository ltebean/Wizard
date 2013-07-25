package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.widget.InvocationContext;
import org.apache.log4j.Logger;

/**
 * @author ltebean
 */
public class DebuggingInterceptor implements Interceptor{

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        long start = System.currentTimeMillis();
        String code=invocation.invoke();
        long end= System.currentTimeMillis();
        System.out.println("---------------"+invocation.getWidget().name+": "+(end-start)+"ms ----------------");
        return code;
    }
}
