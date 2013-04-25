package com.dianping.wizard.config;

import com.dianping.wizard.exception.WizardExeption;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-25
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    private static Configuration configuration ;

    private Config(){

    }

    static {
        try {
            configuration = new PropertiesConfiguration("wizard.properties");
        } catch (ConfigurationException e) {
            throw new WizardExeption("properties file not found");
        }
    }

    public static Configuration getConfiguraion(){
        return configuration;
    }

}
