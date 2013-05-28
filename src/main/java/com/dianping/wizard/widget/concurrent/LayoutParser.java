package com.dianping.wizard.widget.concurrent;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.repo.LayoutRepo;
import com.dianping.wizard.repo.LayoutRepoFactory;
import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.repo.WidgetRepoFactory;
import com.dianping.wizard.script.ScriptEngine;
import com.dianping.wizard.script.ScriptEngineFactory;
import com.dianping.wizard.widget.Layout;
import com.dianping.wizard.widget.RenderingResult;
import com.dianping.wizard.widget.Widget;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author ltebean
 */
public class LayoutParser {

    private final static ScriptEngine engine= ScriptEngineFactory.getEngine("default");

    private final static WidgetRepo widgetRepo= WidgetRepoFactory.getRepo("default");

    private final static LayoutRepo layoutRepo= LayoutRepoFactory.getRepo("default");

    private final static ExecutorService executorService = Executor.getInstance();

    public static Map<String,Future<RenderingResult>> parseAndExecute(Widget widget,String mode,Map<String,Object> param){
        Map<String,Future<RenderingResult>> result=new HashMap<String, Future<RenderingResult>>();
        if(StringUtils.isNotEmpty(widget.layoutRule)){
            widget.layoutName=(String)engine.eval(widget.layoutRule,param);
        }
        if(StringUtils.isNotEmpty(widget.layoutName)){
            Layout layout=layoutRepo.loadByName(widget.layoutName);
            for(Map.Entry<String,List<String>> entry : layout.config.entrySet()) {
                String colKey=entry.getKey();
                for(String widgetName:entry.getValue()){
                    Widget w=widgetRepo.loadByName(widgetName);
                    if(w==null){
                        throw new WidgetException("widget not found: "+widgetName);
                    }
                    if(StringUtils.isEmpty(w.layoutName)&&StringUtils.isEmpty(w.layoutRule)){
                        result.put(w.name,executorService.submit(new RenderingTask(w,mode,param)));
                    } else {
                        result.putAll(parseAndExecute(w,mode,param));
                    }
                }
            }
        }
        return result;
    }

}
