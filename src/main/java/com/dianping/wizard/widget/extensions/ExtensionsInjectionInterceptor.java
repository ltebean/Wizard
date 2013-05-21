package com.dianping.wizard.widget.extensions;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.interceptor.Interceptor;
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
 * Date: 13-4-28
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class ExtensionsInjectionInterceptor implements Interceptor {

    private Map<String,Object> extensions;



    public ExtensionsInjectionInterceptor() {
        extensions=new HashMap<String,Object>();

        String locatorClassName= Configuration.get("extensions.serviceLocator","",String.class);
        if(StringUtils.isNotEmpty(locatorClassName)){
            try{
                ServiceLocator locator=(ServiceLocator)Class.forName(locatorClassName).newInstance();
                extensions.put("extensions",locator);
            }catch (Exception e){
                throw new WidgetException("extensions locator initialization failed",e);
            }
        }

        List<String> modelList=Configuration.get("extensions.staticModels", null, List.class);
        if(CollectionUtils.isNotEmpty(modelList)){
            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
            TemplateHashModel models = wrapper.getStaticModels();
            for (String clazz : modelList) {
                try {
                    TemplateHashModel model =(TemplateHashModel) models.get(clazz);
                    extensions.put(Class.forName(clazz).getSimpleName(),model);
                } catch(Exception e) {
                    throw new WidgetException("static model initialization error",e);
                }
            }
        }
    }

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        if(extensions.size()>0){
            invocation.getContext().putAll(extensions);
        }
        return invocation.invoke();
    }
}
