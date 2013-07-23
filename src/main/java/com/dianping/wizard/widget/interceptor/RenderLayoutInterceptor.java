package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.repo.*;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.script.ScriptEngineFactory;
import com.dianping.wizard.widget.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author ltebean
 */
public class RenderLayoutInterceptor implements Interceptor {

    private final WidgetRepo widgetRepo = WidgetRepoFactory.getRepo("default");

    private final LayoutRepo layoutRepo = LayoutRepoFactory.getRepo("default");

    private final WidgetRenderer renderer = WidgetRendererFactory.getRenderer("default");

    private final ScriptEngine engine = ScriptEngineFactory.getEngine("default");

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        Widget widget = invocation.getWidget();
        if (StringUtils.isNotEmpty(widget.layoutRule)) {
            widget.layoutName = (String) engine.eval(widget.layoutRule, invocation.getContext());
        }
        if (StringUtils.isNotEmpty(widget.layoutName)) {
            Layout layout = layoutRepo.loadByName(widget.layoutName);
            Map<String, Object> param = (Map<String, Object>) invocation.getContext().get("param");
            ResultWrapper wrapper = renderComponents(layout, invocation.getModeType(), param);

            invocation.getContext().put("layout", wrapper.output);
            invocation.setScript(invocation.getScript() + wrapper.script);
        }
        return invocation.invoke();
    }

    private ResultWrapper renderComponents(Layout layout, String mode, Map<String, Object> param) {
        ResultWrapper wrapper = new ResultWrapper();
        StringBuilder scriptBuilder = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : layout.config.entrySet()) {
            String colKey = entry.getKey();
            StringBuilder builder = new StringBuilder();
            for (String widgetName : entry.getValue()) {
                Widget widget = widgetRepo.loadByName(widgetName);
                RenderingResult result = renderer.render(widget, mode, param);
                builder.append(result.output);
                if (widget.modes.get(mode) == null) {
                    continue;
                }
                if (StringUtils.isNotEmpty(result.script)) {
                    scriptBuilder.append(result.script);
                }
            }
            wrapper.output.put(colKey, builder.toString());
        }
        wrapper.script = scriptBuilder.toString();
        return wrapper;
    }

}