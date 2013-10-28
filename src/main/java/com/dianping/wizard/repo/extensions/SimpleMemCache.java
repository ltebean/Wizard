package com.dianping.wizard.repo.extensions;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ltebean
 */
public class SimpleMemCache implements Cache {

    private ConcurrentMap<String,Object> cache=new ConcurrentHashMap<String,Object>();

    @Override
    public void add(String key, Object obj) {
        cache.putIfAbsent(key, obj);
    }

    @Override
    public Object get(String key) {
        return cache.get(key);
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public String generateKey(Class clazz, String name) {
        return clazz.getSimpleName()+name;
    }

    @Override
    public void clearAll() {
        cache.clear();;
    }
}
