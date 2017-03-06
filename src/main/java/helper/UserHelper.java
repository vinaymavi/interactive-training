package helper;

import com.googlecode.objectify.Ref;
import entity.Session;
import entity.User;
import persist.UserOfy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This helper class all supporting function of User Entity.
 */
public class UserHelper {
    private Logger logger = Logger.getLogger(UserHelper.class.getName());

    public Set<String> joinSession(User user, Session session) {
        Set<String> sessionList = user.getSessionList();
        if (sessionList != null && sessionList.size() > 0) {
            sessionList.add(session.getSessionId());
        } else {
            sessionList = new HashSet<>();
            sessionList.add(session.getSessionId());
        }
        user.setSessionList(sessionList);
        UserOfy.save(user);
        return sessionList;
    }
}
