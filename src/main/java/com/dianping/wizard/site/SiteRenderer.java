package com.dianping.wizard.site;

import java.util.Map;

/**
 * @author ltebean
 */
public interface SiteRenderer {

    public String renderSite(Site site, String mode,Map<String,Object> params);

}
