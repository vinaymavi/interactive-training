package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import java.util.HashMap;
import java.util.Map;

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
public class ApiBaseV1 {
    @ApiMethod(name = "api.info",path = "api_info")
    public Map<String,String> getApiDetails(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("api name","reveal");
        return map;
    }
}
