/**
 * 
 */
package com.dianping.wizard.mongo;

import java.net.UnknownHostException;

import com.dianping.wizard.config.Config;
import com.dianping.wizard.config.Constants;
import org.apache.commons.configuration.Configuration;
import org.jongo.Jongo;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * @author cong.yu
 * 
 */
public class JongoClient {

    private static final Configuration config= Config.getConfiguraion();

    private static Jongo jongo;

    private static final String SERVER_ADDRESS =config.getString(Constants.MONGODB_HOST,"127.0.0.1");

    private static final int SERVER_PORT = config.getInt(Constants.MONGODB_PORT,27017);

    private static final String DB_NAME = "test";

    static {
        try {
            Mongo mongo = new Mongo(SERVER_ADDRESS, SERVER_PORT);
            jongo = new Jongo(mongo.getDB(DB_NAME));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static Jongo getInstance() {
        return jongo;
    }

}
