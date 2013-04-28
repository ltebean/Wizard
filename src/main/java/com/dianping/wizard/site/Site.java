package com.dianping.wizard.site;

import com.dianping.wizard.widget.Widget;

/**
 * @author ltebean
 */
public class Site extends Widget{

    public String rule;

    public static Site cloneFromPrototype(Site prototype,String newSiteName){
        Site site=new Site();
        site.name=newSiteName;
        site.rule=prototype.rule;
        site.type=prototype.type;
        site.config=prototype.config;
        site.modes=prototype.modes;
        return site;
    }
}
