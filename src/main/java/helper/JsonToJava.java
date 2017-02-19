package helper;

import entity.webhook.facebook.WebhookPushData;

/**
 * TODO need to check what is the use of this class.
 */
public class JsonToJava {
    public static WebhookPushData toFacebookPushData(String json) {
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.fromJson(json, WebhookPushData.class);
    }
}
