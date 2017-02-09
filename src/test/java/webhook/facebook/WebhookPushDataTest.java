package webhook.facebook;

import entity.webhook.facebook.DataEntry;
import entity.webhook.facebook.Message;
import entity.webhook.facebook.MessageEntry;
import entity.webhook.facebook.WebhookPushData;
import helper.JsonToJava;
import junit.framework.TestCase;

/**
 * Created by vku131 on 2/9/17.
 */
public class WebhookPushDataTest extends TestCase {
    String json ="{'object':'page','entry':[{'id':'107476709764145','time':1486640790769,'messaging':[{'sender':{'id':'1405055952852003'},'recipient':{'id':'107476709764145'},'timestamp':1486640790724,'message':{'mid':'mid.1486640790724:2891d21073','seq':3831,'text':'Hellomoto'}}]}]}";
    WebhookPushData webhookPushData = JsonToJava.toFacebookPushData(json);
    public void testPushData(){
        assertEquals(webhookPushData.getObject(),"page");
    }
    public void testDataEntry(){
        DataEntry dataEntry =webhookPushData.getEntry().get(0);
        assertEquals(dataEntry.getId(),"107476709764145");
    }
    public void testMessageEntry(){
        MessageEntry messageEntry=webhookPushData.getEntry().get(0).getMessaging().get(0);
        assertEquals(messageEntry.getSender().size(),1);
    }

    public void testMessage(){
        MessageEntry messageEntry=webhookPushData.getEntry().get(0).getMessaging().get(0);
        Message message = messageEntry.getMessage();
        assertEquals(message.getMid(),"mid.1486640790724:2891d21073");
    }
}

