package com.dianping.wizard.widget.interceptor.extensions;

import groovy.lang.Closure;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Author: liangjun.zhong
 * Date: 13-12-19
 * Time: AM10:07
 * To change this template use File | Settings | File Templates.
 */
public class CacheableTaskProxy {

    private static final String NULL_RESULT ="NULL_RESULT";

    private final Map<String, Object> cacheableTaskDataRepo;

    public CacheableTaskProxy() {
        cacheableTaskDataRepo = new ConcurrentHashMap<String, Object>();
    }

    /**
     * If there is some data need by multi-widgets, widget can try to get it from the cacheableTaskDataRepo with CacheableTaskProxy which can be put by
     * other widgets. If the data doesn't exist, it will gain the data itself and then put the data in the cacheableTaskDataRepo for other widgets.
     * The lifecycle of the data is only during this request.
     * @param key
     * @param task
     * @return
     */
    public Object run(String key, Closure task) {

        if( StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("cache key must be specified");
        }

        if(task==null){
            throw new IllegalArgumentException("task cannot be null");
        }

        Object result = cacheableTaskDataRepo.get(key);
        if(result == null){
            result = task.call();
            if(result == null){
                cacheableTaskDataRepo.put(key, NULL_RESULT);
            } else {
                cacheableTaskDataRepo.put(key, result);
            }
        }else if(result.equals(NULL_RESULT)){
            result = null;
        }
        return result;
    }
}
