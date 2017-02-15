package persist;

import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import entity.Auth;
import entity.Presentation;
import entity.User;

import java.util.List;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vku131 on 1/21/17.
 */
public class UserOfy {
    public static final Logger logger = Logger.getLogger(UserOfy.class.getName());

    //we do not need instance of this class.
    private UserOfy() {
    }

    public static Key<User> save(User user) {
//        TODO logging missing.
        return ofy().save().entity(user).now();
    }

    public static User loadByKey(Key<User> key) {
        logger.info("key=" + key);
        return ofy().load().key(key).safe();
    }

    public static User loadByFbId(String fbId) {
        logger.info("fbId=" + fbId);
        User user = ofy().load().type(User.class).filter("fbId =", fbId).first().now();
        return user;
    }

    public static User loadBySenderId(String senderId) {
        logger.info("senderId=" + senderId);
        User user = ofy().load().type(User.class).filter("senderId =", senderId).first().now();
        return user;
    }
}
