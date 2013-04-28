package com.dianping.wizard.site.repo;

import com.dianping.wizard.exception.WizardExeption;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public class SiteRepoFactory {

    private static Map<String,SiteRepo> repos;

    static{
        repos=new HashMap<String, SiteRepo>();
        repos.put("default",new SiteRepoImp());
    }


    public static SiteRepo getRepo(String name){
        SiteRepo repo=repos.get(name);
        if (repo == null) {
             throw new WizardExeption("site repo not found:"+name);
        }
        return repo;
    }

}
