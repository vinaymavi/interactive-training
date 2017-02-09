package helper;

import entity.webhook.facebook.WebhookPushData;

/**
 * Created by vku131 on 2/9/17.
 */
public class JsonToJava {
    public static WebhookPushData toFacebookPushData(String json){
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.fromJson(json,WebhookPushData.class);
    }
}
