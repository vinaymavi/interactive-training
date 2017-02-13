package persist;

import com.googlecode.objectify.Key;
import entity.config.Config;

import java.util.List;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vku131 on 2/10/17.
 */
public class ConfigOfy {
    private static Logger logger = Logger.getLogger(ConfigOfy.class.getName());

    public static Key<Config> save(Config config) {
        return ofy().save().entity(config).now();
    }

    public static Config loadByKey(Key<Config> configKey) {
        return ofy().load().key(configKey).safe();
    }

    public static List<Config> loadByGroupName(String groupName) {
        logger.warning("Group Name = " + groupName);
        return ofy().load().type(Config.class).filter("groupName", groupName).list();
    }

    public static List<Config> listAll() {
        logger.warning("List all Config");
        return ofy().load().type(Config.class).list();
    }
}
