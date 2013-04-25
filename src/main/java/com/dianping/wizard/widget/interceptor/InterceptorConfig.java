package com.dianping.wizard.widget.interceptor;

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

    private static List<Interceptor> interceptors;

    static{
        interceptors=new ArrayList<Interceptor>();
        interceptors.add(new MergeInterceptor());
        interceptors.add(new BusinessInterceptor());
    }

    public static Iterator<Interceptor> getInterceptors() {
        return interceptors.iterator();
    }

}
