package com.dianping.wizard.widget;

import com.dianping.wizard.mongo.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */
public class Widget extends Model {

    public static enum ModeType{

        Display("display"),Config("config");

        public final String value;

        ModeType(String value){
            this.value=value;
        }
    }

    public String parentWidgetName="";

    public String layoutRule="";

    public String layoutName="";

    public String type="";

    public Map<String,Mode> modes;

}
