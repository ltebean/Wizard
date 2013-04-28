package com.dianping.wizard.widget;


import java.util.Map;

/**
 * @author ltebean
 */
public interface WidgetRenderer {

    public String renderWidget(Widget widget, String mode,Map<String,Object> params);
}
