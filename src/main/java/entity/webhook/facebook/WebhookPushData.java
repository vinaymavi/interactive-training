package entity.webhook.facebook;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by vku131 on 2/9/17.
 */
@Entity(name = "facebook_message")
public class WebhookPushData {
    @Id
    private Long id;
    private String object;
    @Index
    private String senderId;
    private List<DataEntry> entry;

    public WebhookPushData() {
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public List<DataEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<DataEntry> entry) {
        this.entry = entry;
    }

    @OnSave
    public void renameCoordinates() {
        List<Attachment> attachmentList;
        List<MessageEntry> messageEntries;
        Map<String, Object> payload;
        for (DataEntry dataEntry : entry) {
            messageEntries = dataEntry.getMessaging();
            for (MessageEntry messageEntry :
                    messageEntries) {
                if (messageEntry.getSender() != null) {
                    this.senderId = messageEntry.getSender().get("id");
                }

                attachmentList = messageEntry.getMessage().getAttachments();
                if (attachmentList != null) {
                    for (Attachment attachment :
                            attachmentList) {
                        payload = attachment.getPayload();
                        if (payload != null && payload.containsKey("coordinates")) {
                            Object obj = payload.remove("coordinates");
                            Map<String, String> latLongMap = (Map) obj;
                            payload.put("_lat", latLongMap.get("lat"));
                            payload.put("_long", latLongMap.get("long"));
                        }
                    }
                }
            }
        }
    }
}
