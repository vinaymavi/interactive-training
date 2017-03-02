package persist;

import com.googlecode.objectify.Key;
import entity.Presentation;
import entity.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vku131 on 1/25/17.
 */
public class SessionOfy {
    private static final Logger logger = Logger.getLogger(SessionOfy.class.getName());

    /**
     * @param session {{@link Session}}
     * @return
     */
    public static Key<Session> save(Session session) {

        return ofy().save().entity(session).now();
    }

    /**
     * @param key
     * @return {{@link Session}}
     */
    public static Session loadByKey(Key<Session> key) {
        logger.info("Key = " + key);
        return ofy().load().key(key).safe();
    }

    /**
     * @param sessionId {{@link String}}
     * @return {{@link Session}}
     */
    public static Session loadBySessionId(String sessionId) {
        logger.info("sessionId = " + sessionId);
        return ofy().load().type(Session.class).filter("sessionId", sessionId).first().safe();
    }

    /**
     * @param presentation {{@link Presentation}}
     * @return
     */
    public static List<Presentation> loadByPresentation(Presentation presentation) {
        logger.info("Presentation" + presentation.getName());
        List<Presentation> presentations = ofy().load().type(Presentation.class).ancestor(presentation).list();
        logger.info("Session size=" + presentations.size());
        return presentations;
    }

    /**
     * @param presentationList {{@link List<Presentation>}}
     * @return {{@link List<Session>}}
     */
    public static List<Session> loadByPresentation(List<Presentation> presentationList) {
        logger.info("Size = " + presentationList.size());
        List<Session> sessions = new ArrayList<>();
        for (Presentation p : presentationList) {
            sessions.addAll(ofy().load().type(Session.class).ancestor(p).list());
        }
        logger.info("Session size=" + sessions.size());
        return sessions;
    }
}
