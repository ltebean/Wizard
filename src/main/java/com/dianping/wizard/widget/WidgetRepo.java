package com.dianping.wizard.widget;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public interface WidgetRepo {

    public Widget loadWidget(String id);

    public Widget loadWidgetByName(String name);

    public void addWidget(Widget widget);

    public void updateWidget(Widget widget);

    public void deleteWidget(String id);

}
