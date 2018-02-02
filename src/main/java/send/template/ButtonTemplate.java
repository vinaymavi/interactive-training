package send.template;

import send.Message;
import send.components.ButtonPayload;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by vinaymavi on 17/02/17.
 */
public class ButtonTemplate extends Message {
    private Map<String, Object> message = new HashMap<>();

    /**
     * @param recipientId
     * @param desc
     * @param buttons     List{Object} list of payload buttons
     *                    Message format
     *                    {
     *                    "recipient":{
     *                    "id":"USER_ID"
     *                    },
     *                    "message":{
     *                    "attachment":{
     *                    "type":"template",
     *                    "payload":{
     *                    "template_type":"components",
     *                    "text":"What do you want to do next?",
     *                    "buttons":[
     *                    {
     *                    "type":"web_url",
     *                    "url":"https://petersapparel.parseapp.com",
     *                    "title":"Show Website"
     *                    },
     *                    {
     *                    "type":"postback",
     *                    "title":"Start Chatting",
     *                    "payload":"USER_DEFINED_PAYLOAD"
     *                    }
     *                    ]
     *                    }
     *                    }
     *                    }
     *                    }
     */
    public ButtonTemplate(String recipientId, String desc, List<ButtonPayload> buttons) {
        super(recipientId);
        Map<String, Object> attachment = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        payload.put("template_type", "components");
        payload.put("text", desc);
        payload.put("buttons", buttons);
        attachment.put("type", "template");
        attachment.put("payload", payload);
        this.message.put("attachment", attachment);
    }
}
