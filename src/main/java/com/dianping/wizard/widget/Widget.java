package com.dianping.wizard.widget;

import com.dianping.wizard.mongo.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltebean
 */
public class Widget extends Model {

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

    public static Widget cloneFromPrototype(Widget prototype,String newName){
        Widget widget=new Widget();
        widget.name=newName;
        widget.type=prototype.type;
        widget.config=prototype.config;
        widget.modes=prototype.modes;
        return widget;
    }

}
