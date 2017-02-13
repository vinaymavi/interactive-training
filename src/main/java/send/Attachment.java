package send;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vku131 on 2/13/17.
 */
public class Attachment {
    private Map<String, Object> attachment = new HashMap<>();
    private Map<String, URL> payload = new HashMap<>();

    public Attachment(String type, URL imageURL) {
        this.payload.put("url", imageURL);
        this.attachment.put("type", type);
        this.attachment.put("payload", this.payload);
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }
}
