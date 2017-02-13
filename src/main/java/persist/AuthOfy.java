package persist;

/**
 * Created by vku131 on 1/23/17.
 */

import com.googlecode.objectify.Key;
import entity.Auth;
import entity.User;

import java.util.logging.Logger;

import static persist.OfyService.ofy;

public class AuthOfy {
    private static Logger logger = Logger.getLogger(AuthOfy.class.getName());

    public static Auth createToken(Auth auth) {
        Key<Auth> key = ofy().save().entity(auth).now();
        auth = ofy().load().type(Auth.class).filterKey(key).first().safe();
        logger.info("Generated token = " + auth.getToken());
        return auth;
    }

    public static User getUserByToken(String token) {
        logger.info("token = " + token);
        Auth auth = ofy().load().type(Auth.class).filter("token", token).first().now();
        return auth.getUserRef();
    }
}
