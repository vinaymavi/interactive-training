package send;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by vku131 on 2/10/17.
 */
public class TextMessage extends Message {

    private Map<String, Object> message;

    public TextMessage() {

    }

    public TextMessage(String message) {
        this.message = new HashMap<>();
        this.message.put("text", message);
    }

    public TextMessage(String message, List<QuickReply> quickReplies) {
        this.message = new HashMap<>();
        this.message.put("text", message);
        this.message.put("quick_replies", quickReplies);
    }

    public Map<String, Object> getMessage() {
        return message;
    }

    //    TODO set message as string instead of map.
    public void setMessage(Map<String, Object> message) {
        this.message = message;
    }
}
