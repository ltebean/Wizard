/**
 * 
 */
package com.dianping.wizard.mongo;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.bson.types.ObjectId;

/**
 * @author cong.yu
 * 
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
