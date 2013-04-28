package com.dianping.wizard.widget;

import com.dianping.wizard.exception.WidgetException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */
public class WidgetRendererFactory {

    private static Map<String,WidgetRenderer> renders;

    static{
        renders=new HashMap<String, WidgetRenderer>();
        renders.put("default",new DefaultWidgetRenderer());
    }

    public static WidgetRenderer getRenderer(String name){
        WidgetRenderer renderer =renders.get(name);
        if(renderer==null){
            throw new WidgetException("render not found:"+name);
        }
        return renders.get(name);

    }

    public static void registerWidgetRenderer(String name,WidgetRenderer renderer){
        if(renders.containsKey(name)){
            throw new WidgetException("render key already exists:"+name);
        }
        renders.put(name,renderer);

    }
}
