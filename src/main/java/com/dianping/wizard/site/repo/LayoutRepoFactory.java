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
public class LayoutRepoFactory {

    private static Map<String,LayoutRepo> repos;

    static{
        repos=new HashMap<String, LayoutRepo>();
        repos.put("default",new LayoutRepoImp());
    }

    public static LayoutRepo getRepo(String name){
        LayoutRepo repo=repos.get(name);
        if (repo == null) {
             throw new WizardExeption("layout repo not found:"+name);
        }
        return repo;
    }

}
