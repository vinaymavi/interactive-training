package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiReference;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import entity.Presentation;
import entity.User;
import persist.AuthOfy;
import persist.PresentationOfy;
import persist.UserOfy;

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
public class UserApi {
    Logger logger = Logger.getLogger(UserApi.class.getName());

    @ApiMethod(name = "user.create", httpMethod = ApiMethod.HttpMethod.POST, path = "user_create")
    public User create(User user) {
        logger.info(user.getFbId());
        Key<User> key = UserOfy.save(user);
        return UserOfy.loadByKey(key);
    }

    @ApiMethod(name = "user.get", path = "user_get")
    public User getUserByFbId(@Named("fbId") String fbId) {
        return UserOfy.loadByFbId(fbId);
    }

    @ApiMethod(name = "user.presentation.list", path = "user_presentation_list")
    public List<Presentation> presentationList(@Named("token") String token) {
        logger.info("Token = " + token);
        return PresentationOfy.listByUser(AuthOfy.getUserByToken(token));
    }
}
