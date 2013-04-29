package com.dianping.wizard.repo;

/**
 * @author ltebean
 */
public interface GenericRepo<T> {

    public  T load(String id);

    public  T loadByName(String name);

    public  T save(T t);

    public void delete(String id);

}
