package com.dianping.wizard.widget;

import com.dianping.wizard.exception.WidgetException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
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
