package com.dianping.wizard.repo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ltebean
 */
public class RepoFactory {

    private static ConcurrentMap<String,GenericRepo> repos=new ConcurrentHashMap<String, GenericRepo>();

    public static GenericRepo getRepo(Class clazz){
        String repoName=clazz.getName();
        GenericRepo repo=repos.get(repoName);
        if(repo==null){
            repo=new GenericRepoImpl(clazz);
            repos.putIfAbsent(repoName, repo);
        }
        return repo;
    }

}
