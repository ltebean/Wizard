/**
 * 
 */
package com.dianping.wizard.mongo;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.exception.WizardExeption;
import org.jongo.Jongo;

import com.mongodb.Mongo;

import java.util.Map;

/**
 * @author cong.yu
 */
public class JongoClient {


    private static final Jongo jongo;

    private static final String SERVER_ADDRESS = Configuration.get("mongo.host",String.class);

    private static final int SERVER_PORT = Configuration.get("mongo.port",Integer.class);

    private static final String DB_NAME = Configuration.get("mongo.db",String.class);

    static {
        try {
            Mongo mongo = new Mongo(SERVER_ADDRESS, SERVER_PORT);
            jongo = new Jongo(mongo.getDB(DB_NAME));
        } catch (Exception e) {
            throw new WizardExeption("failed to connect to mongodb",e);
        }
    }

    public static Jongo getInstance() {
        return jongo;
    }

}
