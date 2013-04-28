package com.dianping.wizard.site.repo;

import com.dianping.wizard.site.Layout;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午9:57
 * To change this template use File | Settings | File Templates.
 */
public interface LayoutRepo {

    public void saveLayout(Layout layout);

    public Layout loadLayoutByName(String name);
}
