package com.dianping.wizard.script;

import java.io.File;
import java.util.Map;

/**
 * @author ltebean
 */
public interface ScriptEngine {

    public Object eval(String code,Map<String,Object> context);

    public Object eval(File file,Map<String,Object> context);

}
