package com.dianping.wizard.widget.repo;

import com.dianping.wizard.exception.WidgetException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午9:14
 * To change this template use File | Settings | File Templates.
 */
public class WidgetRepoFactory {

    private static Map<String,WidgetRepo> repos;

    static{
        repos=new HashMap<String, WidgetRepo>();
        repos.put("default",new WidgetRepoImpl());
    }

    public static WidgetRepo getRepo(String name){
        WidgetRepo repo =repos.get(name);
        if(repo==null){
            throw new WidgetException("widget repo not found:"+name);
        }
        return repos.get(name);

    }

    public static void registerRepo(String name,WidgetRepo repo){
        if(repos.containsKey(name)){
            throw new WidgetException("repo key already exists:"+name);
        }
        repos.put(name,repo);

    }

}
