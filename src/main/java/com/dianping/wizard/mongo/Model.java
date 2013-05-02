/**
 * 
 */
package com.dianping.wizard.mongo;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.io.Serializable;

/**
 * @author cong.yu
 */
public class Model implements Serializable{
    @Id
    @ObjectId
    public String id;

}
