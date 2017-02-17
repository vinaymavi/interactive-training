package send.button;

/**
 * Created by vinaymavi on 17/02/17.
 */
public class ButtonPayload {
    /**
     * Button JSON format.
     * {
     * "type":"postback",
     * "title":"Start Chatting",
     * "payload":"USER_DEFINED_PAYLOAD"
     * }
     */
    String type = new String("postback");
    String title;
    String payload;

    public ButtonPayload(String title, String payload) {
        this.title = title;
        this.payload = payload;
    }
}
