package com.dianping.wizard.repo.local;

import com.dianping.wizard.mongo.Model;
import com.dianping.wizard.repo.GenericRepo;

/**
 * @author ltebean
 */
public class GenericLocalRepoImpl<T extends Model> implements GenericRepo<T> {

    @Override
    public T loadByName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<T> find(String query, Object... params) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T save(T t) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(String id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
