package com.dianping.wizard.repo.db;

import com.dianping.wizard.repo.LayoutRepo;
import com.dianping.wizard.widget.Layout;

/**
 * @author ltebean
 */
public class LayoutDBRepo extends GenericDBRepo<Layout> implements LayoutRepo {

    public LayoutDBRepo() {
        super(Layout.class);
    }
}
