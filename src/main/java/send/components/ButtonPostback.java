package send.components;

/**
 * Created by vku131 on 2/2/18.
 */
public class ButtonPostback extends Button {
    private String payload;
    public ButtonPostback(String title) {
        super(title, "postback");
    }

    public ButtonPostback(String title,String payload) {
        super(title, "postback");
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
