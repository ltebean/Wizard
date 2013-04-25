package com.dianping.wizard.widget.merger;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午9:20
 * To change this template use File | Settings | File Templates.
 */
public class FreemarkerUtils {

    public static String merge(String template,Map<String,Object> context) throws Exception {
        return FreemarkerMerger.getInstance().merge(template,context);
    }


}
