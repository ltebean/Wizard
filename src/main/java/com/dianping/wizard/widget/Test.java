package com.dianping.wizard.widget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {

        WidgetRepo repo=new WidgetRepoImpl();
        Widget widget=repo.loadWidget("51764ca7300419f22d26d469");

        Mode displayMode=new Mode();
        displayMode.setCode("shop=['shopId':param.shopId];result=['shop':shop];result.widget=new com.dianping.wizard.widget.Widget();");
        displayMode.setTemplate("shopId:${shop.shopId} widget:${widget}");
        widget.getModes().put("display",displayMode);
        repo.updateWidget(widget);

        Map<String,Object> params=new HashMap<String, Object>();
        params.put("shopId",500000);
        WidgetManager manager=new DefaultWidgetManager();
        System.out.println(manager.renderWidget(widget,Widget.ModeType.Display,params));

    }
}
