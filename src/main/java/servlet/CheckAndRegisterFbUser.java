package servlet;

import com.google.gson.Gson;
import entity.User;
import entity.webhook.facebook.FbUserProfile;
import entity.webhook.facebook.MessageEntry;
import entity.webhook.facebook.WebhookPushData;
import helper.FacebookHelper;
import helper.PayloadHelper;
import persist.UserOfy;
import send.ConversationMessage;
import send.Facebook;
import send.payload.Payload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Task queue call this servlet to process webhook request.
 */
public class CheckAndRegisterFbUser extends HttpServlet {
    private static Gson gson = new Gson();
    private static Logger logger = Logger.getLogger(CheckAndRegisterFbUser.class.getName());
    Facebook facebook;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        facebook = new Facebook();
        logger.warning("Check and Register start");
        String reqPayload = req.getParameter("payload");
        WebhookPushData webhookPushData = gson.fromJson(reqPayload, WebhookPushData.class);
        MessageEntry messageEntry = webhookPushData.getEntry().get(0).getMessaging().get(0);
        String senderId = messageEntry.getSender().get("id");
        User user = null;
        FbUserProfile fbUserProfile = null;
        PayloadHelper payloadHelper;
        Payload payload;
        Map<String, String> quickReplies = null;
        if (webhookPushData.getEntry().get(0).getMessaging().get(0).getMessage() != null) {
            quickReplies = webhookPushData.getEntry().get(0).getMessaging().get(0).getMessage().getQuick_reply();
        }
        String quickReplyPayload = null;

        if (quickReplies != null) {
            quickReplyPayload = quickReplies.get("payload");
        } else if (webhookPushData.getEntry().get(0).getMessaging().get(0).getPostback() != null) {
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
                user = FacebookHelper.FbUserProfileToUser(fbUserProfile, user, senderId);
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
            payload = gson.fromJson(quickReplyPayload, Payload.class);
            payload.setSenderId(senderId);
            user = UserOfy.loadBySenderId(senderId);
            if (user == null) {
                logger.warning("User not registerred for sender id = " + senderId);
            } else {
                payloadHelper = new PayloadHelper(payload);
                payloadHelper.processPayload();
            }
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Payload payload;
        try {
//            Given payload only for testing.
            payload = new Payload("LIST_QUIZ", "NONE");
            logger.info("LIST_QUIZ = " + gson.toJson(payload));
            payload = new Payload("LIST_CURRENT_SESSION", "NONE");
            logger.info("LIST_CURRENT_SESSION = " + gson.toJson(payload));
            payload = new Payload("LIST_SESSION", "NONE");
            logger.info("LIST_SESSION = " + gson.toJson(payload));
            payload = new Payload("LIST_MY_SESSION", "NONE");
            logger.info("LIST_MY_SESSION = " + gson.toJson(payload));
            payload = new Payload("HELP", "NONE");
            logger.info("HELP = " + gson.toJson(payload));
            payload = gson.fromJson("{\"from\":\"ADMIN_MESSAGE\",\"action\":\"LIST_QUIZ\",\"nextAction\":\"NONE\"," +
                    "\"other\":{}}", Payload.class);
            logger.info("Object to JSON" + gson.toJson(payload));
            resp.getWriter().write("<h1>CheckAndRegister is working.</h1>");
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
}
