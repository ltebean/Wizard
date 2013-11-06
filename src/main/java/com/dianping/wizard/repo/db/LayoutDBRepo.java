package com.dianping.wizard.repo.db;

import com.dianping.wizard.repo.LayoutRepo;
import com.dianping.wizard.widget.Layout;

/**
 * @author ltebean
 */
public class LayoutDBRepo extends GenericDBRepo<Layout> implements LayoutRepo {

    public LayoutDBRepo() {
        super(Layout.class);
    }

    @Override
    public  Layout loadByName(String name) {
        if (cache == null) {
            return col.findOne("{name:#}",name).as(Layout.class);
        }
        String key=cache.generateKey(Layout.class,name);
        Layout result= (Layout)cache.get(key);
        if (result == null) {
            result =col.findOne("{name:#}",name).as(Layout.class);
            if (result != null) {
                cache.add(key,result);
            }
        }
        return result;
    }

    @Override
    public  Layout save(Layout layout) {
        col.save(layout);
        if(cache!=null){
            String key=cache.generateKey(Layout.class,layout.name);
            cache.remove(key);
        }
        return layout;
    }

    @Override
    public  Layout updateByName(Layout layout){
        col.update("{name:'"+layout.name+"'}").merge(layout);
        if(cache!=null){
            String key=cache.generateKey(Layout.class,layout.name);
            cache.remove(key);
        }
        return layout;
    }

    @Override
    public void deleteByName(String name) {
        col.remove("{name:#}",name);
        if(cache!=null){
            String key=cache.generateKey(Layout.class,name);
            cache.remove(key);
        }
    }
}
