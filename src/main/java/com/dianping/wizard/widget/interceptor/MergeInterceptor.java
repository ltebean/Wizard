package com.dianping.wizard.widget.interceptor;

import com.dianping.wizard.exception.WidgetException;
import com.dianping.wizard.widget.InvocationContext;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.merger.FreemarkerUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:13
 * To change this template use File | Settings | File Templates.
 */
public class MergeInterceptor implements Interceptor {


    @Override
    public String intercept(InvocationContext invocation) throws Exception {
        String resultCode = invocation.invoke();
        if (resultCode == InvocationContext.NONE) {
            return resultCode;
        }
        Widget widget = invocation.getWidget();
        Mode mode = widget.modes.get(invocation.getModeType());
        if (mode == null) {
            throw new WidgetException("widget(" + widget.name + ") does not support mode:" + invocation.getModeType() + "");
        }
        //merge script and put into the context
        String script="";
        if (StringUtils.isNotEmpty(mode.script)) {
            script= FreemarkerUtils.merge(mode.script, invocation.getContext());
        }
        String allScript=script+invocation.getScript();
        invocation.getContext().put("script",allScript);
        invocation.setScript(allScript);
        //merge html
        if (StringUtils.isNotEmpty(mode.template)) {
            invocation.setOutput(FreemarkerUtils.merge(mode.template, invocation.getContext()));
        }

        return InvocationContext.SUCCESS;

    }
}
