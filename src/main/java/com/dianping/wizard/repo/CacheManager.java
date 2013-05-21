package com.dianping.wizard.repo;

/**
 * @author ltebean
 */
public interface CacheManager{

    public void add(String key,Object obj);

    public Object get(String key);

    public void remove(String key);

    public String generateKey(Class clazz, String name);
}
