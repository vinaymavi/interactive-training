package send.template;

import send.components.Attachment;
import send.Message;

import java.util.HashMap;

/**
 * Created by vinaymavi on 17/02/17.
 */
public class ListTemplate extends Message {
    private HashMap<String, Attachment> message;

    public ListTemplate(String recipientId, Attachment attachment) {
        super(recipientId);
        this.message = new HashMap<>();
        this.message.put("attachment", attachment);
    }
}
