package send;

import com.google.gson.Gson;
import entity.User;
import entity.webhook.facebook.FbUserProfile;
import entity.webhook.facebook.MessageEntry;
import entity.webhook.facebook.WebhookPushData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by vku131 on 2/15/17.
 */
public class ConversationMessage {
    private static Gson gson = new Gson();

    public static String newUserInfoTOAdmin(WebhookPushData webhookPushData, FbUserProfile fbUserProfile) {
        String adminId = "1405055952852003";
        //{"message":{"text":"What is JS?","quick_replies":[{"content_type":"text","title":"Language","payload":"QUESTION NUMEBR"},{"content_type":"text","title":"Framework","payload":"QUESTION NUMEBR"},{"content_type":"text","title":"Library","payload":"QUESTION NUMEBR"}]},"recipient":{"id":"1405055952852003"}}
        TextMessage textMessage = new TextMessage();
        MessageEntry messageEntry = webhookPushData.getEntry().get(0).getMessaging().get(0);
        Map<String, Object> message = new HashMap<>();
        Map<String, String> text = new HashMap<>();

        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply doneQuickReply = new QuickReply("text", "Done");
        doneQuickReply.setPayload("ADMIN_MESSAGE:REGISTRATION:SUCCESS:SEND_WELCOME_MESSAGE:" + messageEntry.getSender().get("id") + "");

        QuickReply wrongInfoQuickReply = new QuickReply("text", "WrongInfo");
        wrongInfoQuickReply.setPayload("ADMIN_MESSAGE:NONE:FAILURE:NONE:" + messageEntry.getSender().get("id") + "");
        quickReplies.add(doneQuickReply);
        quickReplies.add(wrongInfoQuickReply);

        message.put("quick_replies", quickReplies);
        message.put("text", "Admin Message to register user. Facebook Id - " + webhookPushData.getEntry().get(0).getMessaging().get(0).getMessage().getText());
        textMessage.setMessage(message);
        textMessage.setRecipient(adminId);
        return gson.toJson(textMessage);
    }

    public static String welcomeMessage(User user, String senderId) {
        TextMessage textMessage = new TextMessage();
        Map<String, Object> message = new HashMap<>();
        String welcomeStr;

        message.put("text", "Hey "+user.getFirstName()+"," +
                "you have been registered successfully. Thank you for registration :)");
        textMessage.setRecipient(senderId);
        textMessage.setMessage(message);

        welcomeStr = gson.toJson(textMessage);
        return welcomeStr;
    }
}
