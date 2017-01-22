package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiReference;
import com.googlecode.objectify.Key;
import entity.User;
import persist.UserOfy;

/**
 * Created by vku131 on 1/22/17.
 */
@ApiReference(ApiBaseV1.class)
public class UserApi {
    @ApiMethod(name = "user",httpMethod = ApiMethod.HttpMethod.POST)
    public User setUser(){
        Key<User> key = UserOfy.save(new User("123456"));
        return  UserOfy.loadByKey(key);
    }
}
