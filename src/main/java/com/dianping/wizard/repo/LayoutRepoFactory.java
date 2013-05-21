package com.dianping.wizard.repo;

import com.dianping.wizard.exception.WizardExeption;
import com.dianping.wizard.repo.db.LayoutDBRepo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ltebean
 */
public class LayoutRepoFactory {

    private static final ConcurrentMap<String,LayoutRepo> repos;

    static {
        repos=new ConcurrentHashMap<String, LayoutRepo>();
        repos.put("default",new LayoutDBRepo());
    }

    public static LayoutRepo getRepo(String name){
        LayoutRepo repo=repos.get(name);
        if (repo == null) {
            throw new WizardExeption("layout repo not found: "+name);
        }
        return repo;
    }
}
