package com.dianping.wizard.site;

import com.dianping.wizard.mongo.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-27
 * Time: 上午9:35
 * To change this template use File | Settings | File Templates.
 */
public class Layout extends Model {

    public String name;

    public Map<String,List<String>> config=new HashMap<String, List<String>>();

    public Map<String,String> result=new HashMap<String, String>();


}
