package helper;

import entity.Auth;
import entity.User;
import persist.AuthOfy;
import persist.UserOfy;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

/**
 * Created by vku131 on 1/23/17.
 */
public class AuthHelper {
    private static Logger logger = Logger.getLogger(AuthHelper.class.getName());
    private static SecureRandom secureRandom = new SecureRandom();

    public Auth createAuth(String fbId) {
        User user = UserOfy.loadByFbId(fbId);
        if(user == null){
            logger.warning("No user found for fbId = "+fbId);
        }
        String token = this.createToken();
        logger.info("token=" + token);
        Auth auth = new Auth();
        auth.setUserRef(user);
        auth.setToken(token);
        return auth;
    }

    public static String createToken() {
        return new BigInteger(130, secureRandom).toString(32);
    }

    public boolean isValid(String token) {
        if (AuthOfy.getUserByToken(token) != null) {
            return true;
        } else {
            return false;
        }
    }
}
