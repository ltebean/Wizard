package com.dianping.wizard.repo;

import com.dianping.wizard.mongo.Model;

/**
 * @author ltebean
 */
public interface GenericRepo<T extends Model> {

    public  T loadByName(String name);

    public  Iterable<T> find(String query, Object... params);

    public  T save(T t);

    public  T updateByName(T t);

    public void delete(String id);

    public void deleteByName(String name);


}
