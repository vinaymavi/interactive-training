package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import entity.Auth;
import helper.AuthHelper;
import persist.AuthOfy;

import java.util.logging.Logger;

/**
 * Created by vku131 on 1/23/17.
 */
@Api(name = "reveal",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE,},
        title = "Interactive Training",
        description = "Interactive Training Presentation api"

)
public class AuthApi {
    Logger logger = Logger.getLogger(AuthApi.class.getName());
    @ApiMethod(name = "auth.token.create",path = "auth_token_create")
    public Auth create(@Named("fbId") String fbId){
        logger.info("fbId = "+fbId);
        AuthHelper helper = new AuthHelper();
        Auth auth = helper.createAuth(fbId);
        return  AuthOfy.createToken(auth);
    }
}
