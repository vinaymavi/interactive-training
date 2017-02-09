package entity.webhook.facebook;

import java.util.List;

/**
 * Created by vku131 on 2/9/17.
 */
public class DataEntry {
    private String id;
    private double time;
    private List<MessageEntry> messaging;

    public DataEntry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public List<MessageEntry> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<MessageEntry> messaging) {
        this.messaging = messaging;
    }
}
