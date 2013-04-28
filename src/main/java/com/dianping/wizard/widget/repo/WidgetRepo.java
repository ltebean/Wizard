package com.dianping.wizard.widget.repo;

import com.dianping.wizard.widget.Widget;

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

    public void saveWidget(Widget widget);

    public void deleteWidget(String id);

}
