package persist;
import com.googlecode.objectify.Key;
import entity.User;

import java.util.logging.Logger;

import static persist.OfyService.ofy;
/**
 * Created by vku131 on 1/21/17.
 */
public class UserOfy {
    public static final Logger logger = Logger.getLogger(UserOfy.class.getName());
    //we do not need instance of this class.
    private UserOfy(){}
    public static Key<User> save(User user){
        return ofy().save().entity(user).now();
    }

    public static User loadByKey(Key<User> key){
        return  ofy().load().key(key).safe();
    }
}
