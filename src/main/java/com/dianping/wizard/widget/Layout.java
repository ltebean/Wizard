package com.dianping.wizard.widget;

import com.dianping.wizard.mongo.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltebean
 */
public class Layout extends Model {

    public Map<String,List<String>> config=new HashMap<String, List<String>>();

    public static Layout cloneFromPrototype(Layout prototype,String newName){
        Layout layout=new Layout();
        layout.name=newName;
        layout.config=prototype.config;
        return layout;
    }
}
