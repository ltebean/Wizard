package com.dianping.wizard.site.repo;

import com.dianping.wizard.mongo.JongoClient;
import com.dianping.wizard.site.Layout;
import org.jongo.MongoCollection;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public class LayoutRepoImp implements LayoutRepo {

    private MongoCollection col= JongoClient.getInstance().getCollection("layout");

    @Override
    public void saveLayout(Layout layout) {
        col.save(layout);
    }

    @Override
    public Layout loadLayoutByName(String name) {
        return col.findOne("{name:#}",name).as(Layout.class);
    }
}
