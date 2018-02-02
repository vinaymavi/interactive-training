package entity.webhook.facebook;

import com.googlecode.objectify.annotation.Index;

import java.util.Map;

/**
 * Created by vku131 on 2/9/17.
 */
public class MessageEntry {
    private Map<String,String> sender;
    private Map<String,String> recipient;
    private Map<String,String> postback;
    private Map<String,String> referral;
    private double timestamp;
    private Message message;

    public MessageEntry() {
    }

    public Map<String, String> getSender() {
        return sender;
    }

    public void setSender(Map<String, String> sender) {
        this.sender = sender;
    }

    public Map<String, String> getRecipient() {
        return recipient;
    }

    public void setRecipient(Map<String, String> recipient) {
        this.recipient = recipient;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Map<String, String> getPostback() {
        return postback;
    }

    public void setPostback(Map<String, String> postback) {
        this.postback = postback;
    }

    public Map<String, String> getReferral() {
        return referral;
    }

    public void setReferral(Map<String, String> referral) {
        this.referral = referral;
    }
}
