package com.dianping.wizard.repo.extensions;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.widget.WizardInitializationException;
import org.apache.commons.lang.StringUtils;

/**
 * @author ltebean
 */
public class CacheManager {

    private static Cache cache;

    private static final CacheManager instance=new CacheManager();

    private CacheManager(){
        String className= Configuration.get("cache", "", String.class);
        if(StringUtils.isNotEmpty(className)){
            try{
                cache=(Cache)Class.forName(className).newInstance();
            }catch (Exception e){
                throw new WizardInitializationException("cache manager initialization failed",e);
            }
        }
    }

    public static CacheManager getInstance(){
        return instance;
    }

    public Cache getCache(){
        return cache;
    }

}
