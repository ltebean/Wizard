package com.dianping.wizard.repo.db;

import com.dianping.wizard.repo.WidgetRepo;
import com.dianping.wizard.widget.Widget;

/**
 * @author ltebean
 */
public class WidgetDBRepo extends GenericDBRepo<Widget> implements WidgetRepo {

    public WidgetDBRepo() {
        super(Widget.class);
    }
}
