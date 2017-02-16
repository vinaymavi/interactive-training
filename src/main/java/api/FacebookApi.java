package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.gson.Gson;
import helper.FacebookHelper;
import send.Facebook;
import send.ImageMessage;
import send.TextMessage;


import java.net.MalformedURLException;
import java.net.URL;
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
    private static Logger logger = Logger.getLogger(FacebookApi.class.getName());
    private Facebook facebook;
    private Gson gson = new Gson();
    private TextMessage textMessage;
    private ImageMessage imageMessage;
    private Map<String, Object> msgMap;
    private String msgPayload;

    @ApiMethod(name = "facebook.sendtextmsg", path = "facebook_sendtextmsg", httpMethod = "POST")
    public Map<String, String> sendTextMsg(@Named("msg") String msg, @Named("recipient") String recipient, @Nullable @Named("quickReplies") String quickReplies) {
        this.facebook = new Facebook();
        this.textMessage = new TextMessage();
        msgMap = new HashMap<>();
        this.msgMap.put("text", msg);
        this.textMessage.setRecipient(recipient);
        if (quickReplies != null & quickReplies instanceof String) {
            this.msgMap.put("quick_replies",FacebookHelper.StringToQuickReplies(quickReplies));
        }

        this.textMessage.setMessage(msgMap);
        this.msgPayload = gson.toJson(textMessage);
        logger.warning("msgPayload = " + this.msgPayload);
        return facebook.sendMessage(this.msgPayload);
    }

    @ApiMethod(name = "facebook.sendimagemsg", path = "facebook_sendimagemsg", httpMethod = "POST")
    public Map<String, String> sendImageMsg(@Named("msg") String url, @Named("recipient") String recipient, @Nullable @Named("quickReplies") String quickReplies) {
//        TODO implement image size limit.
//        TODO need to handle request timeout.
        this.facebook = new Facebook();
        try {
            this.imageMessage = new ImageMessage(new URL(url));
            this.imageMessage.setRecipient(recipient);
            if (quickReplies != null & quickReplies instanceof String) {
                this.imageMessage.getMessage().put("quick_replies",FacebookHelper.StringToQuickReplies(quickReplies));
            }

        } catch (MalformedURLException mue) {
            logger.warning(mue.getMessage());
        }
//      TODO check toJson execution time.
        this.msgPayload = gson.toJson(imageMessage);
        logger.warning("msgPayload = " + msgPayload);
        return facebook.sendMessage(msgPayload);
    }
}
