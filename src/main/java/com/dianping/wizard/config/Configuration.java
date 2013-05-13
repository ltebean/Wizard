package com.dianping.wizard.config;


import com.dianping.wizard.exception.WizardExeption;
import ognl.Ognl;
import ognl.OgnlException;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * @author ltebean
 */
public class Configuration {

    private static  Map<String,Object> config;

    static {
        try {
            InputStream input = Configuration.class.getResourceAsStream("/wizard.yaml");
            Yaml yaml = new Yaml();
            config = (Map<String,Object>)yaml.load(input);
        } catch(Exception e) {
            throw new WizardExeption("failed to load configuraion: wizard.yaml",e);
        }

    }

    private static Object getConfig(String expression){
        try {
            final Object ognlTree = Ognl.parseExpression(expression);
            return  Ognl.getValue(ognlTree, config);
        } catch (OgnlException e) {
            return null;
        }
    }

    public static <T> T get(String expression,Class<T> clazz){
        return (T) getConfig(expression);
    }

}
