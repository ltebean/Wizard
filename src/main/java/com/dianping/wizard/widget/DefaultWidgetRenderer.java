package com.dianping.wizard.widget;

import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.repo.WidgetRepoFactory;
import com.dianping.wizard.widget.extensions.ExtensionsManager;
import com.dianping.wizard.widget.interceptor.Interceptor;
import com.dianping.wizard.widget.interceptor.InterceptorConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
class DefaultWidgetRenderer implements WidgetRenderer {

    @Override
    public RenderingResult render(Widget widget, String modeType, Map<String, Object> params) {
        if (widget == null) {
            throw new IllegalArgumentException("widget can not be null");
        }
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        String resultCode;
        RenderingResult result = new RenderingResult();
        Iterator<Interceptor> interceptors = InterceptorConfig.getInstance().getInterceptors("default");
        InvocationContext invocation = new InvocationContext(widget, modeType, params, interceptors);
        invocation.getContext().putAll(ExtensionsManager.getInstance().getExtension());
        try {
            resultCode = invocation.invoke();
        } catch (Exception e) {
            throw new WidgetRenderingException("error in rendering widget:" + widget.name, e);
        }
        if (InvocationContext.SUCCESS.equals(resultCode)) {
            result.output = invocation.getOutput();
            result.script = invocation.getScript();
        } else if (InvocationContext.NONE.equals(resultCode)) {
            return result;
        } else {
            throw new WidgetRenderingException("unknown result code-" + resultCode + " returned by widget:" + widget.name);
        }
        return result;
    }

    @Override
    public RenderingResult render(String widgetName, String mode, Map<String, Object> params) {
        WidgetRepo widgetRepo = WidgetRepoFactory.getRepo("default");
        Widget widget = widgetRepo.loadByName(widgetName);
        if (widget == null) {
            throw new IllegalArgumentException("widget not found:" + widgetName);
        }
        return this.render(widget, mode, params);
    }

    @Override
    public RenderingResult render(Widget widget, Map<String, Object> params) {
        return this.render(widget, Widget.ModeType.Display.value, params);
    }

    @Override
    public RenderingResult render(String widgetName, Map<String, Object> params) {
        return this.render(widgetName, Widget.ModeType.Display.value, params);
    }
}
