package persist;

import com.googlecode.objectify.Key;
import entity.Session;

import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vku131 on 1/25/17.
 */
public class SessionOfy {
    private static final Logger logger = Logger.getLogger(SessionOfy.class.getName());

    public static Key<Session> save(Session session) {

        return ofy().save().entity(session).now();
    }

    public static Session loadByKey(Key<Session> key) {
        logger.info("Key = " + key);
        return ofy().load().key(key).safe();
    }

    public static Session loadBySessionId(String sessionId) {
        logger.info("sessionId = " + sessionId);
        return ofy().load().type(Session.class).filter("sessionId", sessionId).first().safe();
    }

}
