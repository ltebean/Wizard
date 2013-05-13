package com.dianping.wizard.widget.extension;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.interceptor.Interceptor;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltebean
 */
public class StaticModelInjectionInterceptor implements Interceptor {

    private Map<String,Object> staticModels;

    public StaticModelInjectionInterceptor() {
        staticModels=new HashMap<String, Object>();
        List<String> modelList=Configuration.get("extension.staticModels",List.class);
        if(CollectionUtils.isEmpty(modelList)){
            return;
        }
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

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        if(staticModels.size()==0){
            return invocation.invoke();
        }
        invocation.getContext().putAll(staticModels);
        return invocation.invoke();
    }
}
