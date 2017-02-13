package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import entity.Presentation;
import entity.Session;
import entity.User;
import helper.AuthHelper;
import persist.PresentationOfy;
import persist.SessionOfy;
import persist.UserOfy;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
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
        User user = UserOfy.loadByFbId(fbId);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        Session session = SessionOfy.loadBySessionId(sessionId);
//        session.setAudience(userList);
        return SessionOfy.loadByKey(SessionOfy.save(session));
    }
}
