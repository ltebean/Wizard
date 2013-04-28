package com.dianping.wizard.widget.repo;

import com.dianping.wizard.mongo.JongoClient;
import com.dianping.wizard.widget.Widget;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午4:44
 * To change this template use File | Settings | File Templates.
 */
class WidgetRepoImpl implements WidgetRepo{

    private MongoCollection col= JongoClient.getInstance().getCollection("widget");

    @Override
    public Widget loadWidget(String id) {
        return col.findOne(new ObjectId(id)).as(Widget.class);
    }

    @Override
    public Widget loadWidgetByName(String name) {
        return col.findOne("{name:#}",name).as(Widget.class);
    }

    @Override
    public void saveWidget(Widget widget) {
        col.save(widget);
    }

    @Override
    public void deleteWidget(String id) {
        col.remove(new ObjectId(id));
    }
}
