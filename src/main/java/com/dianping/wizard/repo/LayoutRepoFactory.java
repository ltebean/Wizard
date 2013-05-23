package com.dianping.wizard.repo;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WizardExeption;
import com.dianping.wizard.repo.db.LayoutDBRepo;
import com.dianping.wizard.repo.db.WidgetDBRepo;
import com.dianping.wizard.repo.local.LayoutLocalRepo;
import com.dianping.wizard.repo.local.WidgetLocalRepo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ltebean
 */
public class LayoutRepoFactory {

    private static final ConcurrentMap<String,LayoutRepo> repos;

    static {
        repos=new ConcurrentHashMap<String, LayoutRepo>();
        String mode= Configuration.get("mode", "local", String.class);
        if(mode.equals("local")){
            repos.put("default",new LayoutLocalRepo());
        }else{
            repos.put("default",new LayoutDBRepo());
        }
    }

    public static LayoutRepo getRepo(String name){
        LayoutRepo repo=repos.get(name);
        if (repo == null) {
            throw new WizardExeption("layout repo not found: "+name);
        }
        return repo;
    }
}
