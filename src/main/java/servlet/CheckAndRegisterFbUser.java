package servlet;

import com.google.gson.Gson;
import entity.User;
import entity.webhook.facebook.FbUserProfile;
import entity.webhook.facebook.Message;
import entity.webhook.facebook.MessageEntry;
import entity.webhook.facebook.WebhookPushData;
import helper.FacebookHelper;
import helper.PayloadHelper;
import persist.UserOfy;
import send.ConversationMessage;
import send.Facebook;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/15/17.
 */
public class CheckAndRegisterFbUser extends HttpServlet {
    private static Gson gson = new Gson();
    private static Logger logger = Logger.getLogger(CheckAndRegisterFbUser.class.getName());
    Facebook facebook = new Facebook();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.warning("Check and Register start");
        String payload = req.getParameter("payload");
        WebhookPushData webhookPushData = gson.fromJson(payload, WebhookPushData.class);
        MessageEntry messageEntry = webhookPushData.getEntry().get(0).getMessaging().get(0);
        String senderId = messageEntry.getSender().get("id");
        User user = null;
        FbUserProfile fbUserProfile = null;
        PayloadHelper payloadHelper;

        Map<String, String> quickReplies = null;
        if (webhookPushData.getEntry().get(0).getMessaging().get(0).getMessage() != null) {
            quickReplies = webhookPushData.getEntry().get(0).getMessaging().get(0).getMessage().getQuick_reply();
        }
        String quickReplyPayload = null;
        String[] payloadItems;
        if (quickReplies != null) {
            quickReplyPayload = quickReplies.get("payload");
        }else if(webhookPushData.getEntry().get(0).getMessaging().get(0).getPostback()!=null){
            quickReplyPayload = webhookPushData.getEntry().get(0).getMessaging().get(0).getPostback().get("payload");
        }
        logger.warning(quickReplyPayload);
        if (quickReplyPayload == null) {
            String profileStr = null;
            profileStr = new Facebook().getUserProfile(senderId);
            logger.warning("ProfileStr" + profileStr);
            fbUserProfile = gson.fromJson(profileStr, FbUserProfile.class);
            user = UserOfy.loadBySenderId(senderId);
            if (fbUserProfile != null) {
                logger.warning("fbUserProfile created");
                user = FacebookHelper.UserProfileToUser(fbUserProfile, user, senderId);
            }
            if (!user.isRegistered()) {
                String adminMsgPayload = ConversationMessage.newUserInfoToAdminButtonTemplate(webhookPushData,
                        fbUserProfile);
                logger.warning("adminMsgPayload" + adminMsgPayload);
                facebook.sendMessage(adminMsgPayload);
                UserOfy.save(user);
            } else {
                logger.warning("User Already registered");
            }
        } else {
            user = UserOfy.loadBySenderId(senderId);
            payloadItems = quickReplyPayload.split(":");
            payloadHelper = new PayloadHelper(payloadItems,senderId);
            payloadHelper.processPayload();
            logger.warning("payloadItems[0]=" + payloadItems[0]);
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().write("<h1>CheckAndRegister is working.</h1>");
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
}
