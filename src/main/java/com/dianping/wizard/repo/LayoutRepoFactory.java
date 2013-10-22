package com.dianping.wizard.repo;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WizardExeption;
import com.dianping.wizard.repo.db.LayoutDBRepo;
import com.dianping.wizard.repo.local.LayoutLocalRepo;

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
        LayoutRepo localRepo=new LayoutLocalRepo();
        LayoutRepo dbRepo=new LayoutDBRepo();
        repos.put("local",localRepo);
        repos.put("db",dbRepo);
        if(mode.equals("local")){
            repos.put("default",localRepo);
        }else{
            repos.put("default",dbRepo);
        }
    }

    public static LayoutRepo getRepo(String name){
        LayoutRepo repo=repos.get(name);
        if (repo == null) {
            throw new WizardExeption("layout repo not found: "+name);
        }
        return repo;
    }

    public static void registerRepo(String name, LayoutRepo repo){
        if(name==null||repo==null){
            throw new IllegalArgumentException("name or repo cannot be null");
        }
        repos.putIfAbsent(name,repo);
    }

    public static void replaceRepo(String name, LayoutRepo repo){
        if(name==null||repo==null){
            throw new IllegalArgumentException("name or repo cannot be null");
        }
        repos.replace(name,repo);
    }
}
