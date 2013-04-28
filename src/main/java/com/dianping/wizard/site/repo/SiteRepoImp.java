package com.dianping.wizard.site.repo;

import com.dianping.wizard.mongo.JongoClient;
import com.dianping.wizard.site.Site;
import org.jongo.MongoCollection;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public class SiteRepoImp implements SiteRepo {

    private MongoCollection col= JongoClient.getInstance().getCollection("site");

    @Override
    public void saveSite(Site site) {
        col.save(site);
    }

    @Override
    public Site loadSiteByName(String name) {
        return col.findOne("{name:#}",name).as(Site.class);
    }
}
