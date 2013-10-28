package com.dianping.wizard.extensions;

import com.dianping.wizard.repo.extensions.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */
public class CacheImpl implements Cache
{
    private Map<String,Object> cache=new HashMap<String,Object>();
    @Override
    public void add(String key, Object obj) {
        cache.put(key,obj);
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
