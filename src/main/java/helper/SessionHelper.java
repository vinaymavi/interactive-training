package helper;

import com.google.gson.Gson;
import entity.Session;
import send.QuickReply;
import send.TextMessage;
import send.payload.Payload;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A collection of {@link entity.Session} helper functions.
 */
public class SessionHelper {
    private static final Logger logger = Logger.getLogger(SessionHelper.class.getName());
    private static final Gson gson = new Gson();
    List<TextMessage> textMessages;
    TextMessage textMessage;
    String message;
    Session session;
    List<QuickReply> quickReplies;
    QuickReply quickReply;
    Map<String, Object> other;

    public List<TextMessage> textMessages(List<Session> sessions, String senderId) {
        textMessages = new ArrayList<>();
        quickReplies = new ArrayList<>();
        Payload payload;
        for (int i = 0; i < sessions.size(); i++) {
            session = sessions.get(i);
            message = "#" + (i + 1) + " " + session.getName() + System.lineSeparator() + session.getDesc();
            quickReply = new QuickReply("" + (i + 1));
            payload = new Payload("SESSION_QUESTION_GROUPS", "NONE");
            other = payload.getOther();
            other.put("sessionId", session.getSessionId());
            quickReply.setPayload(gson.toJson(payload));
            quickReplies.add(quickReply);
            textMessage = new TextMessage(message, quickReplies);
            textMessage.setRecipient(senderId);
            textMessages.add(textMessage);
        }
        return textMessages;
    }
}
