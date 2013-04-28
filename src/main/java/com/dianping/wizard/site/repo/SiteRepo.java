package com.dianping.wizard.site.repo;

import com.dianping.wizard.site.Layout;
import com.dianping.wizard.site.Site;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午9:57
 * To change this template use File | Settings | File Templates.
 */
public interface SiteRepo {

    public void saveSite(Site site);

    public Site loadSiteByName(String name);
}
