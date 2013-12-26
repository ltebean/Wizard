package com.dianping.wizard.utils;

/**
 * @author ltebean
 */
import com.dianping.wizard.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * list resources available from the classpath @ *
 */
public class ResourceList{

    /**
     * for all elements of java.class.path get a Collection of resources Pattern
     * pattern = Pattern.compile(".*"); gets all resources
     *
     * @param pattern
     *            the pattern to match
     * @return the resources in the order they are found
     */
    public static Collection<String> getResources(
            final Pattern pattern){
        final ArrayList<String> retval = new ArrayList<String>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String jarPath = JarUtils.jarPath();
        if (jarPath != null) {
            try {
                retval.addAll(getResourcesInJar(jarPath, pattern));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return retval;
        }

        URL url = classLoader.getResource(Configuration.get("modeConfig.local.basePackage","",String.class));
        try {
            retval.addAll(getResources(new File(url.toURI()), pattern));
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return retval;
    }



    private static Collection<String> getResourcesInJar(
            final String jarPath,
            final Pattern pattern) throws IOException {
        final JarFile jarFile = new JarFile(jarPath);
        final List<String> resources = new ArrayList<String>();
        final Enumeration<JarEntry> jarEntrys = jarFile.entries();

        while (jarEntrys.hasMoreElements()) {
            final JarEntry entry = jarEntrys.nextElement();
            final String resourceName = entry.getName();
            final boolean accept = pattern.matcher(resourceName).matches();
            if (accept) {
                resources.add(resourceName);
                System.out.println(entry.getName() + "found!");
                return resources;
            }
        }
        return resources;
    }

    private static Collection<String> getResources(
            final File file,
            final Pattern pattern){
        final ArrayList<String> retval = new ArrayList<String>();
        if(file.isDirectory()){
            Collection<String> result=getResourcesFromDirectory(file, pattern);
            if(result.size()>0){
                retval.addAll(result);
            }
        }
        return retval;
    }


    private static Collection<String> getResourcesFromDirectory(
            final File directory,
            final Pattern pattern){
        final ArrayList<String> retval = new ArrayList<String>();
        final File[] fileList = directory.listFiles();
        for(final File file : fileList){
            if(file.isDirectory()){
                retval.addAll(getResourcesFromDirectory(file, pattern));
            } else{
                try{
                    final String fileName = file.getCanonicalPath();
                    final boolean accept = pattern.matcher(fileName).matches();
                    if(accept){
                        retval.add(fileName);
                        return retval;
                    }
                } catch(final IOException e){
                    throw new Error(e);
                }
            }
        }
        return retval;
    }
}
