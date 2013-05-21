package com.dianping.wizard.repo.local;

import com.dianping.wizard.config.Yaml;
import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.utils.FileUtils;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */
public class WidgetLocalRepo implements WidgetRepo {
    @Override
    public Widget loadByName(String name) {
        Widget widget=new Widget();
        widget.name=name;
        widget.modes=new HashMap<String, Mode>();
        Mode displayMode=new Mode();
        displayMode.code = FileUtils.readFileOnClassPath(name+"@display","groovy");
        displayMode.template= FileUtils.readFileOnClassPath(name+"@display","ftl");
        displayMode.script= FileUtils.readFileOnClassPath(name+"@display","js","");
        Mode configMode=new Mode();
        configMode.code = FileUtils.readFileOnClassPath(name+"@config","groovy","");
        configMode.template= FileUtils.readFileOnClassPath(name+"@config","ftl","");
        configMode.script= FileUtils.readFileOnClassPath(name+"@config","js","");
        widget.modes.put("display",displayMode);
        widget.modes.put("config",configMode);

        String config= FileUtils.readFileOnClassPath(name,"widget");
        if(StringUtils.isNotEmpty(config)){
            Yaml yaml=new Yaml(new ByteArrayInputStream(config.getBytes()));
            widget.config = yaml.get("config",new HashMap<String, Object>(),Map.class);
            widget.rule=yaml.get("rule","",String.class);
            widget.layoutName=yaml.get("layoutName","",String.class);
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
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }
}
