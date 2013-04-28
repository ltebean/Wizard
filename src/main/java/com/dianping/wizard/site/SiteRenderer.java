package com.dianping.wizard.site;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public interface SiteRenderer {

    public String renderSite(Site site, String mode,Map<String,Object> params);

}
