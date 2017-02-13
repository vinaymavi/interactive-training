package send;

import java.util.Map;

/**
 * Created by vku131 on 2/10/17.
 */
public class TextMessage {
    private Map<String,String> recipient;
    private Map<String, String> message;

    public TextMessage() {
    }

    public Map<String, String> getRecipient() {
        return recipient;
    }

    public void setRecipient(Map<String, String> recipient) {
        this.recipient = recipient;
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }
}
