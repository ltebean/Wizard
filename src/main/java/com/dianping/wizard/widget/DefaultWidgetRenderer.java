package com.dianping.wizard.widget;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.repo.WidgetRepoFactory;
import com.dianping.wizard.widget.interceptor.Interceptor;
import com.dianping.wizard.widget.interceptor.InterceptorConfig;
import org.apache.log4j.Logger;

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

    private Logger logger = Logger.getLogger(this.getClass());

    private WidgetRepo widgetRepo = WidgetRepoFactory.getRepo("default");

    @Override
    public RenderingResult render(Widget widget, String modeType, Map<String, Object> params) {
        if (widget == null) {
            throw new IllegalArgumentException("widget can not be null");
        }
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        Iterator<Interceptor> interceptors = InterceptorConfig.getInterceptors("default");
        InvocationContext invocation = new InvocationContext(widget, modeType, params, interceptors);
        RenderingResult result = new RenderingResult();
        try {
            String resultCode = invocation.invoke();
            if (resultCode == InvocationContext.SUCCESS) {
                result.output = invocation.getOutput();
                result.script = invocation.getScript();
            } else if (resultCode == InvocationContext.NONE) {
                return result;
            } else {
                throw new WidgetException("unknown result code-" + resultCode + " returned by widget:" + widget.name);
            }
        } catch (Exception e) {
            logger.error("rendering error", e);
        }
        return result;
    }

    @Override
    public RenderingResult render(String widgetName, String mode, Map<String, Object> params) {
        Widget widget = widgetRepo.loadByName(widgetName);
        if (widget == null) {
            throw new WidgetException("widget not found:" + widgetName);
        }
        return this.render(widget, mode, params);
    }
}
