package send;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vku131 on 2/13/17.
 */
public class Message {
    private Map<String, String> recipient;

    public Map<String, String> getRecipient() {
        return recipient;
    }


    public void setRecipient(String recipientId) {
        Map<String, String> recipient = new HashMap<>();
        recipient.put("id", recipientId);
        this.recipient = recipient;
    }

    public Message() {
    }

    public Message(String recipientId) {
        Map<String, String> recipient = new HashMap<>();
        recipient.put("id", recipientId);
        this.recipient = recipient;
    }
}
