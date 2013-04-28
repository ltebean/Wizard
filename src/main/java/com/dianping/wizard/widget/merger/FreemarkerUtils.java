package com.dianping.wizard.widget.merger;

import java.util.Map;

/**
 * @author ltebean
 */
public class FreemarkerUtils {

    public static String merge(String template,Map<String,Object> context) throws Exception {
        return FreemarkerMerger.getInstance().merge(template,context);
    }


}
