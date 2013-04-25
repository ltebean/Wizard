package com.dianping.wizard.widget;

import com.dianping.wizard.mongo.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-23
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public class Widget extends Model {

    public enum ModeType{

        Display("display"),Config("config");

        public final String value;

        ModeType(String value){
            this.value=value;
        }
    }

    private String name;

    private String type;

    private Map<String,Object> config;

    private Map<String,Mode> modes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public Map<String, Mode> getModes() {
        return modes;
    }

    public void setModes(Map<String, Mode> modes) {
        this.modes = modes;
    }
}
