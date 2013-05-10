package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.repo.GenericRepo;
import com.dianping.wizard.repo.RepoFactory;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.script.ScriptEngineFactory;
import com.dianping.wizard.widget.*;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author ltebean
 */
public class RenderLayoutInterceptor implements Interceptor {

    private final GenericRepo<Widget> widgetRepo= RepoFactory.getRepo(Widget.class);

    private final WidgetRenderer renderer= WidgetRendererFactory.getRenderer("default");

    private final GenericRepo<Layout> layoutRepo= RepoFactory.getRepo(Layout.class);

    private final ScriptEngine engine= ScriptEngineFactory.getEngine("default");

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        Widget widget=invocation.getWidget();
        if(StringUtils.isNotEmpty(widget.rule)){
             widget.layoutName=(String)engine.eval(widget.rule,invocation.getContext());
        }
        if(StringUtils.isNotEmpty(widget.layoutName)){
            Layout layout=layoutRepo.loadByName(widget.layoutName);
            Map<String,Object> param=(Map< String,Object>) invocation.getContext().get("param");
            ResultWrapper wrapper=renderComponents(layout,invocation.getModeType(),param);
            invocation.getContext().put("layout", wrapper.output);
            invocation.getContext().put("script", wrapper.script);
        }
        return invocation.invoke();
    }

    private ResultWrapper renderComponents(Layout layout,String mode,Map<String,Object> param){
        ResultWrapper wrapper=new ResultWrapper();
        StringBuilder scriptBuilder=new StringBuilder();
        for(Map.Entry<String,List<String>> entry : layout.config.entrySet()) {
            String colKey=entry.getKey();
            StringBuilder builder=new StringBuilder();
            for(String widgetName:entry.getValue()){
                Widget widget=widgetRepo.loadByName(widgetName);
                builder.append(renderer.renderWidget(widget,mode,param));
                if(widget.modes.get(mode)==null){
                    continue;
                }
                if(StringUtils.isNotEmpty(widget.modes.get(mode).script)){
                    scriptBuilder.append(widget.modes.get(mode).script);
                }
            }
            wrapper.output.put(colKey,builder.toString());
        }
        wrapper.script=scriptBuilder.toString();
        return wrapper;
    }
}
