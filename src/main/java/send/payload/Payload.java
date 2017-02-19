package send.payload;

import java.util.HashMap;
import java.util.Map;

/**
 * Facebook messenger work with payload.
 * We need to send payload with message and when User clicks to the button or quick replies the same payload
 * messenger send to the webhook.
 */
public class Payload {
    private static final String DEFAULT_FROM = "ADMIN_MESSAGE";
    private String from;
    private String action;
    private String nextAction;
    /**
     * MessengerId is used for special case when message need to send to provided user.
     * Ex. When app mode in development then new user can send a request to admin just typing facebook id.
     * and admin can add him/her as developer/testing and reply back.
     */
    private String messengerId;
    private String senderId;
    private Map<String, Object> other;

    public Payload() {
    }

    /**
     * @param action     {String}
     * @param nextAction {String}
     * @param senderId   {String}
     */
    public Payload(String action, String nextAction, String senderId) {
        this.from = DEFAULT_FROM;
        this.action = action;
        this.nextAction = nextAction;
        this.senderId = senderId;
        this.other = new HashMap<>();
    }

    /**
     * @param action     {String}
     * @param nextAction {String}
     */
    public Payload(String action, String nextAction) {
        this.from = DEFAULT_FROM;
        this.action = action;
        this.nextAction = nextAction;
        this.other = new HashMap<>();
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public String getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(String messengerId) {
        this.messengerId = messengerId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Map<String, Object> getOther() {
        return other;
    }

    /**
     * Push a new entry to other map.
     * @param key {String}
     * @param value {String}
     */
    public void setOther(String key, Object value) {
        this.other.put(key, value);
    }
}
