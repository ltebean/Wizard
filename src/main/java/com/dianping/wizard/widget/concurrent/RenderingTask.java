package com.dianping.wizard.widget.concurrent;

import com.dianping.wizard.widget.RenderingResult;
import com.dianping.wizard.widget.Widget;
import com.dianping.wizard.widget.WidgetRenderer;
import com.dianping.wizard.widget.WidgetRendererFactory;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author ltebean
 */
public class RenderingTask implements Callable<RenderingResult>{

    private final Widget widget;

    private final String mode;

    private final Map<String,Object> param;

    private final WidgetRenderer render = WidgetRendererFactory.getRenderer("default");

    public RenderingTask(Widget widget,String mode, Map<String,Object> param) {
        this.widget = widget;
        this.mode=mode;
        this.param=param;
    }

    @Override
    public RenderingResult call() throws Exception {
        return render.render(widget,mode,param);
    }
}
