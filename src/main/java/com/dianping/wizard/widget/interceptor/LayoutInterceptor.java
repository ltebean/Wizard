package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.repo.LayoutRepo;
import com.dianping.wizard.repo.LayoutRepoFactory;
import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.repo.WidgetRepoFactory;
import com.dianping.wizard.script.Script;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.script.ScriptEngineFactory;
import com.dianping.wizard.widget.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author ltebean
 */
public class LayoutInterceptor implements Interceptor {

    private final WidgetRenderer renderer = WidgetRendererFactory.getRenderer("default");

    private final ScriptEngine engine = ScriptEngineFactory.getEngine("default");

    private final Logger logger = Logger.getLogger(this.getClass());

    private final int timeout = Configuration.get("concurrent.timeout", 1000, Integer.class);


    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        Widget widget = invocation.getWidget();
        String layoutName="";
        //evaluate the layout
        if(StringUtils.isNotEmpty(widget.layoutName)){
            layoutName=widget.layoutName;
        }else if(StringUtils.isNotEmpty(widget.layoutRule)){
            String scriptName= Script.generateName(widget.name, "layout");
            layoutName=(String)engine.eval(new Script(scriptName,widget.layoutRule),invocation.getContext());
        }
        if (StringUtils.isNotEmpty(layoutName)) {
            LayoutRepo layoutRepo = LayoutRepoFactory.getRepo("default");
            Layout layout = layoutRepo.loadByName(layoutName);
            if(layout==null){
                throw new WidgetException("layout not found: "+layoutName);
            }
            Map<String, Object> param = invocation.getParam();
            Map<String, Future<RenderingResult>> tasks = (Map<String, Future<RenderingResult>>) param.get("CONCURRENT_TASKS");

            ResultWrapper wrapper = renderComponentsFromPool(layout, invocation.getModeType(), param,tasks);

            invocation.getContext().put("layout", wrapper.output);
            invocation.setScript(invocation.getScript() + wrapper.script);
        }
        return invocation.invoke();
    }

    private ResultWrapper renderComponentsFromPool(Layout layout, String mode, Map<String, Object> param, Map<String, Future<RenderingResult>> tasks) {
        WidgetRepo widgetRepo = WidgetRepoFactory.getRepo("default");
        ResultWrapper wrapper = new ResultWrapper();
        StringBuilder scriptBuilder = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : layout.config.entrySet()) {
            String colKey = entry.getKey();
            StringBuilder builder = new StringBuilder();
            for (String widgetName : entry.getValue()) {
                Widget widget = widgetRepo.loadByName(widgetName);
                if (widget.modes.get(mode) == null) {
                    continue;
                }
                RenderingResult result = null;
                if(tasks==null||tasks.get(widgetName)==null){
                    result = renderer.render(widget, mode, param);
                }else{
                    try {
                        result = tasks.get(widgetName).get(timeout, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        String msg="failed to retrieve result - widget:" + widget.name;
                        logger.error(msg, new WidgetException(msg,e));
                    }
                }

                if (result != null && StringUtils.isNotEmpty(result.output)) {
                    builder.append(result.output);
                }

                if (result != null && StringUtils.isNotEmpty(result.script)) {
                    scriptBuilder.append(result.script);
                }
            }
            wrapper.output.put(colKey, builder.toString());
        }
        wrapper.script = scriptBuilder.toString();
        return wrapper;
    }

}