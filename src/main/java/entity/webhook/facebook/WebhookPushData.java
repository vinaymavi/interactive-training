package entity.webhook.facebook;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.List;

/**
 * Created by vku131 on 2/9/17.
 */
@Entity(name = "facebook_message")
public class WebhookPushData {
    @Id
    private Long id;
    private String object;
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
}
