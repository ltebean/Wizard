package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.widget.InvocationContext;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:02
 * To change this template use File | Settings | File Templates.
 */
public interface Interceptor {

    public String intercept(InvocationContext invocation) throws Exception;
}
