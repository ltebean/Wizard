package com.dianping.wizard.repo.local;

import com.dianping.wizard.config.Yaml;
import com.dianping.wizard.exception.WizardExeption;
import com.dianping.wizard.repo.extensions.Cache;
import com.dianping.wizard.repo.extensions.CacheManager;
import com.dianping.wizard.repo.WidgetRepo;
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
public class WidgetLocalRepo implements WidgetRepo {

    private final Logger logger= Logger.getLogger(this.getClass());

    private  final Cache cache;

    public WidgetLocalRepo() {
        cache = CacheManager.getCache();
    }

    @Override
    public Widget loadByName(String name) {
        if(cache == null){
            Widget widget= constructWidget(name);
            populateWithParent(widget);
            return widget;
        }
        String key=cache.generateKey(Widget.class,name);
        Widget widget=(Widget)cache.get(key);
        if (widget == null) {
            widget=constructWidget(name);
            if (widget != null) {
                cache.add(key,widget);
                populateWithParent(widget);
            }
        }
        return widget;
    }

    private Widget constructWidget(String name){
        Widget widget=new Widget();
        widget.name=name;
        widget.modes=new HashMap<String, Mode>();
        Mode displayMode=new Mode();
        displayMode.code = FileUtils.readFileOnClassPath(name,"groovy");
        displayMode.template= FileUtils.readFileOnClassPath(name,"ftl");
        displayMode.script= FileUtils.readFileOnClassPath(name,"js","");
        Mode configMode=new Mode();
        configMode.code = FileUtils.readFileOnClassPath(name+"@config","groovy","");
        configMode.template= FileUtils.readFileOnClassPath(name+"@config","ftl","");
        configMode.script= FileUtils.readFileOnClassPath(name+"@config","js","");
        widget.modes.put("display",displayMode);
        widget.modes.put("config",configMode);

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

    private void populateWithParent(Widget widget) {
        if (StringUtils.isEmpty(widget.parentWidgetName)) {
            return;
        }
        Mode displayMode = widget.modes.get(Widget.ModeType.Display.value);
        if (StringUtils.isEmpty(displayMode.code) || StringUtils.isEmpty(displayMode.script) || StringUtils.isEmpty(displayMode.template) || StringUtils.isEmpty(widget.layoutRule) || StringUtils.isEmpty(widget.layoutName)) {
            Widget parent = this.loadByName(widget.parentWidgetName);
            if(parent==null){
                throw new WizardExeption("parent not found:"+widget.parentWidgetName);
            }
            Mode parentDisplayMode = parent.modes.get(Widget.ModeType.Display.value);
            if (StringUtils.isEmpty(widget.layoutName)) {
                widget.layoutName = parent.layoutName;
            }
            if (StringUtils.isEmpty(widget.layoutRule)) {
                widget.layoutRule = parent.layoutRule;
            }
            if (StringUtils.isEmpty(displayMode.code)) {
                displayMode.code = parentDisplayMode.code;
            }
            if (StringUtils.isEmpty(displayMode.template)) {
                displayMode.template = parentDisplayMode.template;
            }
            if (StringUtils.isEmpty(displayMode.script)) {
                displayMode.script = parentDisplayMode.script;
            }
        }
    }

}
