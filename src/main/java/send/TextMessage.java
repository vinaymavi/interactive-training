package send;

import java.util.Map;


/**
 * Created by vku131 on 2/10/17.
 */
public class TextMessage extends Message {

    private Map<String, Object> message;

    public TextMessage() {
    }

    public Map<String, Object> getMessage() {
        return message;
    }
    //    TODO set message as string instead of map.
    public void setMessage(Map<String, Object> message) {
        this.message = message;
    }
}
