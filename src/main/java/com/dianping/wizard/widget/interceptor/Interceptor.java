package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.widget.InvocationContext;

/**
 * @author ltebean
 */
public interface Interceptor {

    public String intercept(InvocationContext invocation) throws Exception;
}
