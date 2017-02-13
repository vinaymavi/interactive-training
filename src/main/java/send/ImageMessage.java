package send;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vku131 on 2/13/17.
 */
public class ImageMessage extends Message {
    private static String MESSAGE_KEY = "message";
    private static String MESSAGE_TYPE = "image";
    private Map<String, Object> message = new HashMap<>();
    private Attachment attachment;

    public ImageMessage(URL imageUrl) {
        this.attachment = new Attachment(MESSAGE_TYPE, imageUrl);
        this.message.put("attachment", this.attachment.getAttachment());
    }

    public Map<String, Object> getMessage() {
        return message;
    }

}
