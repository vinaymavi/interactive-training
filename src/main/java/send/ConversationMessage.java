package send;

import com.google.gson.Gson;
import entity.webhook.facebook.FbUserProfile;
import entity.webhook.facebook.MessageEntry;
import entity.webhook.facebook.WebhookPushData;
import send.button.ButtonPayload;
import send.template.ButtonTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by vku131 on 2/15/17.
 */
public class ConversationMessage {
    private static Gson gson = new Gson();

    @Deprecated
    public static String newUserInfoTOAdmin(WebhookPushData webhookPushData, FbUserProfile fbUserProfile) {
        String adminId = "1405055952852003";
        //{"message":{"text":"What is JS?","quick_replies":[{"content_type":"text","title":"Language",
        // "payload":"QUESTION NUMEBR"},{"content_type":"text","title":"Framework","payload":"QUESTION NUMEBR"},
        // {"content_type":"text","title":"Library","payload":"QUESTION NUMEBR"}]},
        // "recipient":{"id":"1405055952852003"}}
        TextMessage textMessage = new TextMessage();
        MessageEntry messageEntry = webhookPushData.getEntry().get(0).getMessaging().get(0);
        Map<String, Object> message = new HashMap<>();
        Map<String, String> text = new HashMap<>();

        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply doneQuickReply = new QuickReply("text", "Done");
        doneQuickReply.setPayload("ADMIN_MESSAGE:REGISTRATION:SUCCESS:SEND_MESSAGE:" + messageEntry.getSender().get
                ("id") + "");

        QuickReply wrongInfoQuickReply = new QuickReply("text", "WrongInfo");
        wrongInfoQuickReply.setPayload("ADMIN_MESSAGE:REGISTRATION:FAILURE:NONE:" + messageEntry.getSender().get
                ("id") + "");
        quickReplies.add(doneQuickReply);
        quickReplies.add(wrongInfoQuickReply);

        message.put("quick_replies", quickReplies);
        message.put("text", "Admin Message to register user. Facebook Id - " + webhookPushData.getEntry().get(0)
                .getMessaging().get(0).getMessage().getText());
        textMessage.setMessage(message);
        textMessage.setRecipient(adminId);
        return gson.toJson(textMessage);
    }

    public static String newUserInfoToAdminButtonTemplate(WebhookPushData webhookPushData, FbUserProfile
            fbUserProfile) {
        String adminId = "1405055952852003";
        String text = "Admin Message to register user. Facebook Id - " + webhookPushData.getEntry().get(0)
                .getMessaging().get(0).getMessage().getText();
        MessageEntry messageEntry = webhookPushData.getEntry().get(0).getMessaging().get(0);
        List<ButtonPayload> buttonPayloads = new ArrayList<>();
        buttonPayloads.add(new ButtonPayload("Done", "ADMIN_MESSAGE:REGISTRATION:SUCCESS:SEND_MESSAGE:" +
                messageEntry.getSender().get("id") + ""));
        buttonPayloads.add(new ButtonPayload("WrongInfo", "ADMIN_MESSAGE:REGISTRATION:FAILURE:NONE:" + messageEntry
                .getSender().get("id") + ""));
        ButtonTemplate buttonTemplate = new ButtonTemplate(adminId, text, buttonPayloads);
        return gson.toJson(buttonTemplate);
    }
}
