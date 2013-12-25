package com.dianping.wizard.widget;

import com.dianping.wizard.mongo.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ltebean
 */
public class Layout extends Model {

    public String author="";

    public Map<String,List<String>> config=new HashMap<String, List<String>>();

}
