package send.components;

/**
 * Created by vku131 on 2/2/18.
 */
public class Attachment {
    private  String type;
    private Payload payload;

    public Attachment(String type, Payload payload) {
        this.type = type;
        this.payload = payload;
    }

}
