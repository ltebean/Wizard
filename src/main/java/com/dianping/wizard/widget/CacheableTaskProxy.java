package com.dianping.wizard.widget;

import groovy.lang.Closure;
import org.apache.log4j.Logger;

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


    private final Logger logger = Logger.getLogger(this.getClass());

    private Map<String, Object> cacheableTaskDataRepo = new ConcurrentHashMap<String, Object>();
    private class EmptyResult {};
    private final EmptyResult EMPTYRESULT = new EmptyResult();


    /**
     * If there is some data need by multi-widgets, widget can try to get it from the cacheableTaskDataRepo with CacheableTaskProxy which can be put by
     * other widgets. If the data doesn't exist, it will gain the data itself and then put the data in the cacheableTaskDataRepo for other widgets.
     * The lifecycle of the data is only during this request.
     * @param key
     * @param task
     * @return
     */
    public Object run(String key, Closure task) {

        if( key==null || "".equals(key) || task==null){
            return null;
        }

        Object result = cacheableTaskDataRepo.get(key);

        if(result == null){
            result = task.call();

            if(result == null){
                cacheableTaskDataRepo.put(key, EMPTYRESULT);
            } else {
                cacheableTaskDataRepo.put(key, result);
            }
        }else if(result == EMPTYRESULT){
            result = null;
        }

        return result;
    }




}
