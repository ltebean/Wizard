/**
 * 
 */
package com.dianping.wizard.mongo;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * @author cong.yu
 */
public class Model {
    @Id
    @ObjectId
    public String id;

}
