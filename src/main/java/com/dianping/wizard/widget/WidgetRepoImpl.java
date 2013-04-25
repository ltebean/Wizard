package com.dianping.wizard.widget;

import com.dianping.wizard.mongo.JongoClient;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import java.util.HashMap;
import java.util.Map;

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
    public void addWidget(Widget widget) {
        col.save(widget);
    }

    @Override
    public void updateWidget(Widget widget) {
        col.save(widget);
    }

    @Override
    public void deleteWidget(String id) {
        col.remove(new ObjectId(id));
    }
}
