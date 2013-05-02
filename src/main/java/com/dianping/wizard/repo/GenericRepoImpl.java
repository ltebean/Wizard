package com.dianping.wizard.repo;

import com.dianping.wizard.mongo.JongoClient;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

/**
 * @author ltebean
 */
public class GenericRepoImpl<T> implements GenericRepo<T> {

    protected MongoCollection col;

    private Class<T> clazz;

    public GenericRepoImpl(Class<T> clazz) {
        this.clazz =clazz;
        this.col= JongoClient.getInstance().getCollection(StringUtils.lowerCase(clazz.getSimpleName()));
    }

    @Override
    public  T load(String id) {
        return col.findOne(new ObjectId(id)).as(clazz);
    }

    @Override
    public  T loadByName(String name) {
        return col.findOne("{name:#}",name).as(clazz);
    }

    @Override
    public  T save(T t) {
        col.save(t);
        return t;
    }

    @Override
    public void delete(String id) {
        col.remove(new ObjectId(id));
    }
}
