package send;

/**
 * Created by vku131 on 2/25/17.
 */
public class ActionTyping extends Message {
    private String sender_action;

    public ActionTyping() {
        this.sender_action = "typing_on";
    }
}
