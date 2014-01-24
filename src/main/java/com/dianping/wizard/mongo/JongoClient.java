/**
 * 
 */
package com.dianping.wizard.mongo;

import com.dianping.wizard.config.Configuration;
import com.dianping.wizard.widget.WizardInitializationException;
import com.mongodb.Mongo;
import org.apache.commons.lang.StringUtils;
import org.jongo.Jongo;

/**
 * @author cong.yu
 */
public class JongoClient {

    private static final Jongo jongo;

    private static final String SERVER_ADDRESS = Configuration.get("modeConfig.db.server.host",String.class);

    private static final int SERVER_PORT = Configuration.get("modeConfig.db.server.port",Integer.class);

    private static final String DB_NAME = Configuration.get("modeConfig.db.dbName",String.class);

    private static final String MONGO_FACTORY=Configuration.get("modeConfig.db.mongoFactory","",String.class);
    static {
        try {
            Mongo mongo;
            if(StringUtils.isNotEmpty(MONGO_FACTORY)){
                MongoFactory mongoFactory=(MongoFactory)Class.forName(MONGO_FACTORY).newInstance();
                mongo=mongoFactory.getMongo();
            }else{
                mongo = new Mongo(SERVER_ADDRESS, SERVER_PORT);
            }
            jongo = new Jongo(mongo.getDB(DB_NAME));
        } catch (Exception e) {
            throw new WizardInitializationException("failed to initialize mongo client",e);
        }
    }

    public static Jongo getInstance() {
        return jongo;
    }

}
