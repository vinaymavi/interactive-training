package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import entity.Presentation;
import entity.User;
import persist.AuthOfy;
import persist.PresentationOfy;

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
public class PresentationApi {
    private static Logger logger = Logger.getLogger(PresentationApi.class.getName());

    @ApiMethod(name = "presentation.create", path = "presentation_create")
    public Presentation create(@Named("token") String token, Presentation presentation) {
        User user = AuthOfy.getUserByToken(token);
        if (user == null) {
//            TODO Exception required.
            logger.warning("Null user object");
        }
        presentation.setUserRef(user);
        return PresentationOfy.loadByKey(PresentationOfy.save(presentation));
    }

    /**
     * Get User by presentation id.
     *
     * @param {{String}} presentationId
     * @return {{User}}
     */
    @ApiMethod(name = "presentation.user", path = "presentation_user")
    public User getUser(@Named("presentationId") String presentationId) {
        logger.info("presentationId = " + presentationId);
        return PresentationOfy.loadById(presentationId).getUserRef();
    }
}
