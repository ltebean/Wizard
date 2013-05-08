package com.dianping.wizard.site;

import com.dianping.wizard.repo.GenericRepo;
import com.dianping.wizard.repo.RepoFactory;
import com.dianping.wizard.script.DefaultScriptEngine;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.script.ScriptEngineFactory;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.WidgetRenderer;
import com.dianping.wizard.widget.WidgetRendererFactory;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltebean
 */
class SiteRendererImpl implements SiteRenderer {

    private final GenericRepo<Widget> widgetRepo= RepoFactory.getRepo(Widget.class);

    private final WidgetRenderer renderer= WidgetRendererFactory.getRenderer("default");

    private final GenericRepo<Layout> layoutRepo= RepoFactory.getRepo(Layout.class);

    private final ScriptEngine engine= ScriptEngineFactory.getEngine("default");

    @Override
    public String renderSite(Site site, String mode, Map<String, Object> params) {
        Layout layout=layoutRepo.loadByName(getLayoutName(site.rule, params));
        ResultWrapper wrapper =renderComponents(layout, mode, params);
        Map<String,Object> siteParam=new HashMap<String, Object>();
        siteParam.put("layout", wrapper.output);
        siteParam.put("script", wrapper.script);
        return renderer.renderWidget(site,mode,siteParam);
    }

    private String getLayoutName(String rule, Map<String, Object> params){
        return (String)engine.eval(rule,params);
    }


    private ResultWrapper renderComponents(Layout layout,String mode,Map<String,Object>param){
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
