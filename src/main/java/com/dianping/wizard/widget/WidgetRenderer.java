package com.dianping.wizard.widget;


import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public interface WidgetRenderer {

    public String renderWidget(Widget widget, String mode,Map<String,Object> params);
}
