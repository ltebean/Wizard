package com.dianping.wizard.config;

import com.dianping.wizard.exception.WizardExeption;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @author ltebean
 */
public class Config {
    private static final Configuration configuration ;

    private Config(){}

    static {
        try {
            configuration = new PropertiesConfiguration("wizard.properties");
        } catch (ConfigurationException e) {
            throw new WizardExeption("properties file not found, 'wizard.properties' must exist in the classpath");
        }
    }

    public static Configuration getConfiguraion(){
        return configuration;
    }

}
