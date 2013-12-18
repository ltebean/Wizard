package com.dianping.wizard.repo.extensions;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WidgetException;
import org.apache.commons.lang.StringUtils;

/**
 * @author ltebean
 */
public class CacheManager {

    private static Cache cache;

    static {
        String className= Configuration.get("cache", "", String.class);
        if(StringUtils.isNotEmpty(className)){
            try{
                cache=(Cache)Class.forName(className).newInstance();
            }catch (Exception e){
                throw new WidgetException("cache manager initialization failed",e);
            }
        }
    }

    public static Cache getCache(){
        return cache;
    }

}
