package com.dianping.wizard.service;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-28
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLocator implements com.dianping.wizard.widget.extension.ServiceLocator{
    @Override
    public Object get(String serviceName) {
        return new shopService();
    }
}

class shopService{
    public String getShopName(int shopId){
        return "kfc";
    }
}
