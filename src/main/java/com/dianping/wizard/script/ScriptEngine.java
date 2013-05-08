package com.dianping.wizard.script;

import java.util.Map;

/**
 * @author ltebean
 */
public interface ScriptEngine {

    public Object eval(String code,Map<String,Object> context);
}
