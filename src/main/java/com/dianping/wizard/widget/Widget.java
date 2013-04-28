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
    public static void main(String[] args) {

    }


    public enum ModeType{

        Display("display"),Config("config");

        public final String value;

        ModeType(String value){
            this.value=value;
        }
    }

    public String name;

    public String type;

    public Map<String,Object> config;

    public Map<String,Mode> modes;

}
