package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiReference;
import com.googlecode.objectify.Key;
import entity.User;
import persist.UserOfy;

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
    @ApiMethod(name = "user.create", httpMethod = ApiMethod.HttpMethod.POST,path = "user_create")
    public User createUser(User user) {
        logger.info(user.getFbId());
        Key<User> key = UserOfy.save(user);
        return UserOfy.loadByKey(key);
    }
}
