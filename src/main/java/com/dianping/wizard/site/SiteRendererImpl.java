package com.dianping.wizard.site;

import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.site.repo.LayoutRepo;
import com.dianping.wizard.site.repo.LayoutRepoFactory;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.WidgetRenderer;
import com.dianping.wizard.widget.WidgetRendererFactory;
import com.dianping.wizard.widget.repo.WidgetRepo;
import com.dianping.wizard.widget.repo.WidgetRepoFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
public class SiteRendererImpl implements SiteRenderer {

    private WidgetRepo widgetRepo= WidgetRepoFactory.getRepo("default");

    private WidgetRenderer renderer= WidgetRendererFactory.getRenderer("default");

    private LayoutRepo layoutRepo= LayoutRepoFactory.getRepo("default");


    private ScriptEngine engine=ScriptEngine.getInstance();

    @Override
    public String renderSite(Site site, String mode, Map<String, Object> params) {
        Layout layout=layoutRepo.loadLayoutByName(getLayoutName(site.rule,params));
        ResultWrapper wrapper =renderComponents(layout, mode, params);
        Map<String,Object> siteParam=new HashMap<String, Object>();
        siteParam.put("output", wrapper.output);
        siteParam.put("script", wrapper.script);
        return renderer.renderWidget(site,mode,siteParam);
    }

    private String getLayoutName(String rule, Map<String, Object> params){
        return (String)engine.eval(params,rule);
    }


    private ResultWrapper renderComponents(Layout layout,String mode,Map<String,Object>param){
        ResultWrapper wrapper=new ResultWrapper();
        for(Map.Entry<String,List<String>> entry : layout.config.entrySet()) {
            String colKey=entry.getKey();
            String s="";
            for(String widgetName:entry.getValue()){
                Widget widget=widgetRepo.loadWidgetByName(widgetName);
                s+=renderer.renderWidget(widget,mode,param);
                wrapper.script+=  widget.modes.get(mode).script;
            }
            wrapper.output.put(colKey,s);
        }
        return wrapper;
    }
}
