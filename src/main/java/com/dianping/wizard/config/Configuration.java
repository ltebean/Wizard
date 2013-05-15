package com.dianping.wizard.config;

import java.io.InputStream;

/**
 * @author ltebean
 */
public class Configuration {

    private static final Yaml yaml;

    static{
        InputStream input=Configuration.class.getResourceAsStream("/wizard.yaml");
        yaml=new Yaml(input);
    }


    public static <T> T get(String expression,Class<T> clazz){
        return yaml.get(expression,clazz);
    }

}
