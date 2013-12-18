package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.merger.FreemarkerUtils;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */
public class MergeInterceptor implements Interceptor {

    private Map<String,Object> staticModels=new HashMap<String,Object>();

    public MergeInterceptor() {
        List<String> modelList= Configuration.get("freemarker.staticModels", null, List.class);
        if(CollectionUtils.isNotEmpty(modelList)){
            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
            TemplateHashModel models = wrapper.getStaticModels();
            for (String clazz : modelList) {
                try {
                    TemplateHashModel model =(TemplateHashModel) models.get(clazz);
                    staticModels.put(Class.forName(clazz).getSimpleName(),model);
                } catch(Exception e) {
                    throw new WidgetException("static model initialization error",e);
                }
            }
        }
    }

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        String resultCode = invocation.invoke();
        if (InvocationContext.NONE.equals(resultCode)) {
            return resultCode;
        }
        Widget widget = invocation.getWidget();
        Mode mode = widget.modes.get(invocation.getModeType());
        if (mode == null) {
            throw new WidgetException("widget(" + widget.name + ") does not support mode:" + invocation.getModeType() + "");
        }
        //merge script and put into the context
        invocation.getContext().putAll(staticModels);
        String script="";
        if (StringUtils.isNotEmpty(mode.script)) {
            script= FreemarkerUtils.merge(widget.name+invocation.getModeType()+"script",mode.script, invocation.getContext());
        }
        String allScript=script+invocation.getScript();
        invocation.getContext().put("script",allScript);
        invocation.setScript(allScript);
        //merge html
        if (StringUtils.isNotEmpty(mode.template)) {
            invocation.setOutput(FreemarkerUtils.merge(widget.name+invocation.getModeType()+"code",mode.template, invocation.getContext()));
        }

        return InvocationContext.SUCCESS;

    }
}
