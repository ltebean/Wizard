package com.dianping.wizard.site;

import com.dianping.wizard.exception.WizardExeption;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */
public class SiteRendererFactory {

    private static final Map<String,SiteRenderer> renderers;

    static{
        renderers=new HashMap<String, SiteRenderer>();
        renderers.put("default",new SiteRendererImpl());
    }

    public static SiteRenderer getRenderer(String name){
        SiteRenderer renderer=renderers.get(name);
        if(renderer==null){
            throw new WizardExeption("site renderer not found:"+name);
        }
        return renderer;
    }

    public static void registerRenderer(String name,SiteRenderer renderer){
        renderers.put(name,renderer);
    }

}
