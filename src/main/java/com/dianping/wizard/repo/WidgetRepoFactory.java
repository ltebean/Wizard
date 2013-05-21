package com.dianping.wizard.repo;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WizardExeption;
import com.dianping.wizard.repo.db.WidgetDBRepo;
import com.dianping.wizard.repo.local.WidgetLocalRepo;
import com.dianping.wizard.widget.Widget;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ltebean
 */
public class WidgetRepoFactory {

    private static final ConcurrentMap<String,WidgetRepo> repos;

    static {
        repos=new ConcurrentHashMap<String, WidgetRepo>();
        String mode= Configuration.get("mode","dev",String.class);
        if(mode.equals("dev")){
            repos.put("default",new WidgetLocalRepo());
        }else{
            repos.put("default",new WidgetDBRepo());
        }
    }

    public static WidgetRepo getRepo(String name){
        WidgetRepo repo=repos.get(name);
        if (repo == null) {
            throw new WizardExeption("widget repo not found: "+name);
        }
        return repo;
    }
}
