package send;

import java.util.Map;

/**
 * Created by vku131 on 2/13/17.
 */
public class Message {
    private Map<String, String> recipient;

    public Map<String, String> getRecipient() {
        return recipient;
    }

    public void setRecipient(Map<String, String> recipient) {
        this.recipient = recipient;
    }
}
