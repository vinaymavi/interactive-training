package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import entity.Presentation;
import entity.Session;
import entity.User;
import helper.AuthHelper;
import persist.PresentationOfy;
import persist.SessionOfy;
import persist.UserOfy;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by vku131 on 1/22/17.
 */
@Api(name = "reveal",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE,},
        title = "Interactive Training",
        description = "Interactive Training Presentation api"

)
public class SessionApi {
    private static final Logger logger = Logger.getLogger(SessionApi.class.getName());

    @ApiMethod(name = "session.create", path = "session_create")
    public Session create(@Named("token") String token, @Named("pId") String pId, Session session) {
        Presentation presentation = PresentationOfy.loadByPresentationId(pId);
        session.setPresentationRef(presentation);
        session.setSessionId(AuthHelper.createToken());
//        TODO logging required.
        return SessionOfy.loadByKey(SessionOfy.save(session));
    }

    @ApiMethod(name = "session.get", path = "session_get")
    public Session getBySessionId(@Named("token") String token, @Named("sessionId") String sessionId) {
        logger.info("sessionId = " + sessionId);
        return SessionOfy.loadBySessionId(sessionId);
    }

    @ApiMethod(name = "session.addAudience")
    public Session addAudience(@Named("token") String token, @Named("sessionId") String sessionId, @Named("fbId") String fbId) {
        Session session = SessionOfy.loadBySessionId(sessionId);
        User user = UserOfy.loadByFbId(fbId);
        Set<User> userList = session.getAudience();
        if (userList == null) {
            userList = new HashSet<>();
            ;
            userList.add(user);
        } else {
            userList.add(user);
        }
        session.setAudience(userList);
        return SessionOfy.loadByKey(SessionOfy.save(session));
    }

    @ApiMethod(name = "session.pushFeedback", path = "session_push_feedback")
    public Map<String, String> pushFeedback(@Named("token") String token, @Named("sessionId") String sessionId) {
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder.withUrl("/send_feedback_message_fb").param("sid", sessionId));
        Map<String, String> resp = new HashMap<>();
        resp.put("status", "OK");
        resp.put("message", "Task queue initialized");
        return resp;
    }

    @ApiMethod(name = "session.upcoming", path = "session_upcoming", httpMethod = "GET")
    public List<Session> upcomingSessions() {
        List<Session> sessionList = SessionOfy.upcomingSessions();
        logger.info("Size = " + sessionList.size());
        return sessionList;
    }

    @ApiMethod(name = "session.current", path = "session_current", httpMethod = "GET")
    public List<Session> currentSessions() {
        List<Session> sessionList = SessionOfy.currentSessions();
        logger.info("Size = " + sessionList.size());
        return sessionList;
    }

    @ApiMethod(name = "session.startSession", path = "session_start_session", httpMethod = "GET")
    public Session startSession(@Named("sessionId") String sessionId) {
        logger.info("sessionId =" + sessionId);
        Session session = SessionOfy.loadBySessionId(sessionId);
        session.setLive(true);
        SessionOfy.save(session);
        session = SessionOfy.loadBySessionId(sessionId);
        return session;
    }

    @ApiMethod(name = "session.endSession", path = "session_end_session", httpMethod = "GET")
    public Session endSession(@Named("sessionId") String sessionId) {
        logger.info("sessionId =" + sessionId);
        Session session = SessionOfy.loadBySessionId(sessionId);
        session.setEnd(true);
        SessionOfy.save(session);
        session = SessionOfy.loadBySessionId(sessionId);
        return session;
    }
}
