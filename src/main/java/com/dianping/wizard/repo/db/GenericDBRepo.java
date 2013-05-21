package com.dianping.wizard.repo.db;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.mongo.JongoClient;
import com.dianping.wizard.mongo.Model;
import com.dianping.wizard.repo.CacheManager;
import com.dianping.wizard.repo.GenericRepo;
import org.apache.commons.lang.StringUtils;
import org.jongo.MongoCollection;

/**
 * @author ltebean
 */
public class GenericDBRepo<T extends Model> implements GenericRepo<T> {

    protected final MongoCollection col;

    private final Class<T> clazz;

    private CacheManager cache;

    public GenericDBRepo(Class<T> clazz) {
        String className= Configuration.get("extensions.cacheManager","", String.class);
        if(StringUtils.isNotEmpty(className)){
            try{
                cache=(CacheManager)Class.forName(className).newInstance();
            }catch (Exception e){
                throw new WidgetException("cache manager initialization failed",e);
            }
        }
        this.clazz =clazz;
        this.col= JongoClient.getInstance().getCollection(StringUtils.lowerCase(clazz.getSimpleName()));
    }

    @Override
    public  T loadByName(String name) {
        if (cache == null) {
            return col.findOne("{name:#}",name).as(clazz);
        }
        String key=cache.generateKey(clazz,name);
        T result= (T) cache.get(key);
        if (result == null) {
            result =col.findOne("{name:#}",name).as(clazz);
            cache.add(key,result);
        }
        return result;
    }

    @Override
    public  Iterable<T> find(String query, Object... params) {
        return col.find(query, params).as(clazz);
    }

    @Override
    public  T save(T t) {
        col.save(t);
        if(cache!=null){
            String key=cache.generateKey(clazz,t.name);
            cache.remove(key);
        }
        return t;
    }

    @Override
    public void delete(String name) {
        col.remove("{name:#}",name);
    }
}