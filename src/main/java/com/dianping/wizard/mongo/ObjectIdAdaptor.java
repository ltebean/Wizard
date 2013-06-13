package com.dianping.wizard.mongo;

import org.bson.types.ObjectId;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author ltebean
 */
public class ObjectIdAdaptor extends XmlAdapter<String, ObjectId> {

    @Override
    public String marshal(ObjectId v) throws Exception {
        return v.toString();
    }

    @Override
    public ObjectId unmarshal(String v) throws Exception {
        return new ObjectId(v);
    }

}

