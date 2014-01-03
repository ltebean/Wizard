package com.dianping.wizard.widget.java;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.repo.java.WidgetControllerRepo;
import com.dianping.wizard.utils.SpringContext;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.interceptor.Interceptor;

import java.util.Map;

/**
 * @author ltebean
 */
public class JavaBusinessInterceptor implements Interceptor{

    private WidgetControllerRepo widgetControllerRepo;

    public JavaBusinessInterceptor() {
        this.widgetControllerRepo = (WidgetControllerRepo) SpringContext.getBean("widgetControllerRepo");
    }

    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        Widget widget=invocation.getWidget();

        Mode mode=widget.modes.get(invocation.getModeType());
        if(mode==null){
            throw new WidgetException("widget("+widget.name+") does not support mode:"+invocation.getModeType()+"");
        }

        WidgetController controller=widgetControllerRepo.loadByName(widget.name);
        Map<String,Object> result=controller.run(invocation.getParam());
        if(result!=null && result instanceof Map){
            invocation.getContext().putAll(result);
        }
        return InvocationContext.SUCCESS;
    }
}
