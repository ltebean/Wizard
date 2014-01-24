package com.dianping.wizard.widget.extensions;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.widget.WizardInitializationException;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-28
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class ExtensionsManager {

    private final Map<String,Object> extensions=new HashMap<String, Object>();

    private static final ExtensionsManager instance=new ExtensionsManager();

    private ExtensionsManager() {
        Map<String,String> extensionsConfig = Configuration.get("extensions",new HashMap<String, Object>(),Map.class);
        if(extensionsConfig.size()==0){
            return;
        }
        try{
            for (String extensionsName : extensionsConfig.keySet()) {
                if(StringUtils.isNotEmpty(extensionsName)){
                        Object extensionsInstance=Class.forName(extensionsConfig.get(extensionsName)).newInstance();
                        extensions.put(extensionsName,extensionsInstance);
                    }
                }
            }
        catch (Exception e){
            throw new WizardInitializationException("extensions initialization failed",e);
        }
    }

    public static ExtensionsManager getInstance(){
        return instance;
    }

    public Map<String,Object> getExtension() {
        return extensions;
    }
}
