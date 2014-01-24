package com.dianping.wizard.widget;


import java.util.Map;

/**
 * @author ltebean
 */
public interface WidgetRenderer {

    public RenderingResult render(Widget widget, String mode, Map<String, Object> params) throws WidgetRenderingException;

    public RenderingResult render(String widgetName, String mode, Map<String, Object> params) throws WidgetRenderingException;

    public RenderingResult render(Widget widget,Map<String, Object> params) throws WidgetRenderingException;

    public RenderingResult render(String widgetName,Map<String, Object> params) throws WidgetRenderingException;

}
