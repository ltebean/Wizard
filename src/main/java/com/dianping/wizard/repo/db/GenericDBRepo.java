package com.dianping.wizard.repo.db;

import com.dianping.wizard.mongo.JongoClient;
import com.dianping.wizard.mongo.Model;
import com.dianping.wizard.repo.extensions.Cache;
import com.dianping.wizard.repo.extensions.CacheManager;
import com.dianping.wizard.repo.GenericRepo;
import org.apache.commons.lang.StringUtils;
import org.jongo.MongoCollection;

/**
 * @author ltebean
 */
public class GenericDBRepo<T extends Model> implements GenericRepo<T> {

    protected final MongoCollection col;

    private final Class<T> clazz;

    protected final Cache cache;

    public GenericDBRepo(Class<T> clazz) {
        this.cache = CacheManager.getCache();
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
            if (result != null) {
                cache.add(key,result);
            }
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
    public  T updateByName(T t){
        col.update("{name:'"+t.name+"'}").merge(t);
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

    @Override
    public void deleteByName(String name) {
        col.remove("{name:#}",name);
    }

}
