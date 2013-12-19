package com.dianping.wizard.widget.merger;

import java.util.Map;

/**
 * @author ltebean
 */
public interface Merger {

    public String merge(Template template, Map<String, Object> context) throws Exception ;

}
