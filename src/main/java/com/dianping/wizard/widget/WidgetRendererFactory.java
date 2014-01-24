package com.dianping.wizard.widget;

import com.dianping.wizard.widget.concurrent.ConcurrentRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */
public class WidgetRendererFactory {

    private static final Map<String,WidgetRenderer> renders;

    static{
        renders=new HashMap<String, WidgetRenderer>();
        renders.put("default",new DefaultWidgetRenderer());
        renders.put("concurrent",new ConcurrentRenderer());
    }

    public static WidgetRenderer getRenderer(String name){
        WidgetRenderer renderer =renders.get(name);
        if(renderer==null){
            throw new RuntimeException("render not found:"+name);
        }
        return renders.get(name);

    }

    public static void registerWidgetRenderer(String name,WidgetRenderer renderer){
        if(renders.containsKey(name)){
            throw new IllegalArgumentException("renderer already exists:"+name);
        }
        renders.put(name,renderer);

    }
}
