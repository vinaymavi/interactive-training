package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.gson.Gson;
import send.Facebook;
import send.TextMessage;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/10/17.
 */
@Api(name = "reveal",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE,},
        title = "Interactive Training",
        description = "Interactive Training Presentation api"

)
public class FacebookApi {
    Logger logger = Logger.getLogger(FacebookApi.class.getName());
    private Facebook facebook;
    private Gson gson = new Gson();
    private TextMessage textMessage;
    private Map<String, String> msgMap;
    private Map<String, String> recipientMap;
    private String msgPayload;

    @ApiMethod(name = "facebook.sendtextmsg", path = "facebook_sendtextmsg", httpMethod = "POST")
    public Map<String, String> sendTextMsg(@Named("msg") String msg, @Named("recipient") String recipient) {
        facebook = new Facebook();
        textMessage = new TextMessage();
        msgMap = new HashMap<>();
        recipientMap = new HashMap<>();
        msgMap.put("text", msg);
        recipientMap.put("id",recipient);
        textMessage.setRecipient(recipientMap);
        textMessage.setMessage(msgMap);
        msgPayload = gson.toJson(textMessage);
        logger.warning("msgPayload = " + msgPayload);
        return facebook.sendTextMessage(msgPayload);
    }
}
