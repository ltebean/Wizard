package com.dianping.wizard.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 13-12-26
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
public abstract class JarUtils {

    public static String jarPath() {
        String classPath = System.getProperty("java.class.path");
        List<String> classRepository = new ArrayList<String>();

        if ( (classPath != null) && ! (classPath.equals(""))) {
            StringTokenizer tokenizer = new StringTokenizer(classPath,
                    File.pathSeparator);
            while (tokenizer.hasMoreTokens()) {
                classRepository.add(tokenizer.nextToken());
            }
        }
        if (classRepository.size() == 1) {
            return classRepository.get(0);
        }
        return null;
    }
}
