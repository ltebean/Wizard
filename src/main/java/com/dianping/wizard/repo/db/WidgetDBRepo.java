package com.dianping.wizard.repo.db;

import com.dianping.wizard.exception.WizardExeption;
import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.widget.Mode;
import com.dianping.wizard.widget.Widget;
import org.apache.commons.lang.StringUtils;

/**
 * @author ltebean
 */
public class WidgetDBRepo extends GenericDBRepo<Widget> implements WidgetRepo {

    public WidgetDBRepo() {
        super(Widget.class);
    }


    @Override
    public Widget loadByName(String name) {
        Widget widget;
        if (cache == null) {
            widget = col.findOne("{name:#}", name).as(Widget.class);
            populateWithParent(widget);
            return widget;
        }
        String key = cache.generateKey(Widget.class, name);
        widget = (Widget) cache.get(key);
        if (widget == null) {
            widget = col.findOne("{name:#}", name).as(Widget.class);
            if (widget != null) {
                populateWithParent(widget);
                cache.add(key, widget);
            }
        }
        return widget;
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
