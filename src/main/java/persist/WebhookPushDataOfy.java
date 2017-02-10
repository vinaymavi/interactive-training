package persist;

import com.googlecode.objectify.Key;
import entity.webhook.facebook.WebhookPushData;

import static persist.OfyService.ofy;

/**
 * Created by vku131 on 2/10/17.
 */
public class WebhookPushDataOfy {
    public static Key<WebhookPushData> save(WebhookPushData webhookPushData) {
        return ofy().save().entity(webhookPushData).now();
    }
}
