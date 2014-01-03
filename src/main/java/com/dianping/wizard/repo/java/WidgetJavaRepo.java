package com.dianping.wizard.repo.java;

import com.dianping.wizard.config.Yaml;
import com.dianping.wizard.exception.WizardExeption;
import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.repo.extensions.Cache;
import com.dianping.wizard.repo.extensions.CacheManager;
import com.dianping.wizard.utils.FileUtils;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

/**
 * @author ltebean
 */
public class WidgetJavaRepo implements WidgetRepo {
    private final Logger logger= Logger.getLogger(this.getClass());

    private  final Cache cache;

    public WidgetJavaRepo() {
        cache = CacheManager.getCache();
    }

    @Override
    public Widget loadByName(String name) {
        if(cache == null){
            Widget widget= constructWidget(name);
            return widget;
        }
        String key=cache.generateKey(Widget.class,name);
        Widget widget=(Widget)cache.get(key);
        if (widget == null) {
            widget=constructWidget(name);
            if (widget != null) {
                cache.add(key,widget);
            }
        }
        return widget;
    }

    private Widget constructWidget(String name){
        Widget widget=new Widget();
        widget.name=name;
        widget.modes=new HashMap<String, Mode>();
        Mode displayMode=new Mode();
        displayMode.template= FileUtils.readFileOnClassPath(name,"ftl");
        displayMode.script= FileUtils.readFileOnClassPath(name,"js","");
        widget.modes.put("display",displayMode);

        try {
            String config= FileUtils.readFileOnClassPath(name,"widget");
            if(StringUtils.isNotEmpty(config)){
                Yaml yaml=new Yaml(new ByteArrayInputStream(config.getBytes()));
                widget.layoutRule=yaml.get("layoutRule","",String.class);
                widget.layoutName=yaml.get("layoutName","",String.class);
                widget.parentWidgetName=yaml.get("parentWidgetName","",String.class);

            }
        } catch(Exception e) {
            logger.warn("no .widget file for: "+name);
        }

        return widget;
    }

    @Override
    public Iterable<Widget> find(String query, Object... params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Widget save(Widget widget) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Widget updateByName(Widget widget) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteByName(String name) {
        throw new UnsupportedOperationException();
    }


}
