package servlet;

import com.google.gson.Gson;
import entity.User;
import entity.webhook.facebook.FbUserProfile;
import entity.webhook.facebook.MessageEntry;
import entity.webhook.facebook.WebhookPushData;
import helper.FacebookHelper;
import helper.GenericTemplateHelper;
import helper.PayloadHelper;
import persist.UserOfy;
import send.ConversationMessage;
import send.Facebook;
import send.components.ResponsePayload;
import send.template.GenericTemplate;

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
        String senderId;
        User user = null;
        FbUserProfile fbUserProfile = null;
        PayloadHelper payloadHelper;
        ResponsePayload payload;
        Map<String, String> quickReplies = null;
        String payloadString = null;
        logger.warning("Check and Register start");

        String reqPayload = req.getParameter("payload");

        logger.info("Request Payload = " + reqPayload);

        WebhookPushData webhookPushData = gson.fromJson(reqPayload, WebhookPushData.class);
        MessageEntry messageEntry = webhookPushData.getEntry().get(0).getMessaging().get(0);
        senderId = messageEntry.getSender().get("id");

        //       quick replies and postback have two different keys.
        if (webhookPushData.getEntry().get(0).getMessaging().get(0).getMessage() != null
                && webhookPushData.getEntry().get(0).getMessaging().get(0).getMessage().getQuick_reply() != null) {

            payloadString = webhookPushData.getEntry().get(0).getMessaging().get(0).getMessage().getQuick_reply().get("payload");

        } else if (webhookPushData.getEntry().get(0).getMessaging().get(0) != null
                && webhookPushData.getEntry().get(0).getMessaging().get(0).getPostback() != null) {

            payloadString = webhookPushData.getEntry().get(0).getMessaging().get(0).getPostback().get("payload");

        } else if (webhookPushData.getEntry().get(0).getMessaging().get(0) != null
                && webhookPushData.getEntry().get(0).getMessaging().get(0).getReferral() != null) {
            payloadString = gson.fromJson(webhookPushData.getEntry().get(0).getMessaging().get(0).getReferral().get("ref"), String.class);
        }


        logger.warning(payloadString);
//        TODO: need to remvoe all console errors resolved.
        if (payloadString == "DEVELOPER_DEFINED_PAYLOAD") {
            return;
        }
        if (payloadString == null) {
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
                GenericTemplate genericTemplate = GenericTemplateHelper.createMessage(webhookPushData, fbUserProfile, messageEntry);
                String adminMsgPayload = gson.toJson(genericTemplate);
                logger.warning("adminMsgPayload" + adminMsgPayload);
                facebook.sendMessage(adminMsgPayload);
                UserOfy.save(user);
            } else {
//                TODO send not able to understand message.
                logger.warning("User Already registered and we are not able to understand the query.");
            }
        } else {
            payload = gson.fromJson(payloadString, ResponsePayload.class);
            payload.setSenderId(senderId);
            user = UserOfy.loadBySenderId(senderId);
            if (user == null) {
                logger.warning("User not registered for sender id = " + senderId);
            } else {
                payloadHelper = new PayloadHelper(payload);
                payloadHelper.processPayload();
            }
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ResponsePayload payload;
        try {
//            Given payload only for testing.
            payload = new ResponsePayload("LIST_QUIZ", "NONE");
            logger.info("LIST_QUIZ = " + gson.toJson(payload));
            payload = new ResponsePayload("LIST_CURRENT_SESSION", "NONE");
            logger.info("LIST_CURRENT_SESSION = " + gson.toJson(payload));
            payload = new ResponsePayload("LIST_SESSION", "NONE");
            logger.info("LIST_SESSION = " + gson.toJson(payload));
            payload = new ResponsePayload("LIST_MY_SESSION", "NONE");
            logger.info("LIST_MY_SESSION = " + gson.toJson(payload));
            payload = new ResponsePayload("HELP", "NONE");
            logger.info("HELP = " + gson.toJson(payload));
            payload = gson.fromJson("{\"from\":\"ADMIN_MESSAGE\",\"action\":\"LIST_QUIZ\",\"nextAction\":\"NONE\"," +
                    "\"other\":{}}", ResponsePayload.class);
            logger.info("Object to JSON" + gson.toJson(payload));
            resp.getWriter().write("<h1>CheckAndRegister is working.</h1>");
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
}
