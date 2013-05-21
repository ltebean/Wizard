package com.dianping.wizard.repo;

import com.dianping.wizard.repo.db.GenericDBRepo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ltebean
 */
public class GenericRepoFactory {

    private static final ConcurrentMap<String,GenericRepo> repos=new ConcurrentHashMap<String, GenericRepo>();

    public static GenericRepo getGenericRepo(Class clazz){
        String repoName=clazz.getName();
        GenericRepo repo=repos.get(repoName);
        if(repo==null){
            repo=new GenericDBRepo(clazz);
            repos.putIfAbsent(repoName, repo);
        }
        return repo;
    }
}
