package helper;

import com.google.gson.Gson;
import entity.Session;
import entity.User;
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
    private String JOIN_MSG = "Want to join?";
    private String ATTEND_MSG = "Want to attend?";
    private static final Gson gson = new Gson();
    List<TextMessage> textMessages;
    TextMessage textMessage;
    String message;
    Session session;
    List<QuickReply> quickReplies;
    QuickReply quickReply;
    Map<String, Object> other;

    /**
     * Create text message for my session as trainer.
     *
     * @param sessions {{@link List<Session>}}
     * @param senderId {{@link String}}
     * @return {{@link List<TextMessage>}}
     */
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

    /**
     * Create text message upcoming/current sessions.
     *
     * @param list     {{@link List<Session>}}
     * @param senderId {{@link String}}
     * @return {{@link List<TextMessage>}}
     */
    public List<TextMessage> sessions(List<Session> list, String senderId) {
        textMessages = new ArrayList<>();
        quickReplies = new ArrayList<>();
        Payload payload;
        for (int i = 0; i < list.size(); i++) {
            session = list.get(i);
            message = "#" + (i + 1) + " " + session.getName() + System.lineSeparator() + session.getDesc();
            quickReply = new QuickReply("" + (i + 1));
            payload = new Payload("SESSION_ACTIONS", "NONE");
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

    /**
     * @param user    {{@link User}}
     * @param session {{@link Session}}
     * @return {{@link TextMessage}}
     */
    public TextMessage sessionActions(User user, Session session) {

        if (session.getLive() != null && session.getLive()) {
            this.joinSession(user, session);
        } else {
            this.attendSession(user, session);
        }
        return this.textMessage;
    }

    private TextMessage joinSession(User user, Session session) {

        quickReplies = new ArrayList<>();
        //Yes
        quickReply = new QuickReply("Yes :)");
        Payload payload = new Payload("JOIN_SESSION", "JOIN_SESSION_CONFIRM");
        payload.setOther("sessionId", session.getSessionId());
        quickReply.setPayload(gson.toJson(payload));
        quickReplies.add(quickReply);

        //No
        quickReply = new QuickReply("Nope :(");
        payload = new Payload("LIST_CURRENT_SESSION", "NONE");
        payload.setOther("sessionId", session.getSessionId());
        quickReply.setPayload(gson.toJson(payload));
        quickReplies.add(quickReply);

        textMessage = new TextMessage(this.JOIN_MSG, quickReplies);
        textMessage.setRecipient(user.getSenderId());

        return textMessage;
    }

    private TextMessage attendSession(User user, Session session) {
        quickReplies = new ArrayList<>();
        //Yes
        quickReply = new QuickReply("Yes :)");
        Payload payload = new Payload("ATTEND_SESSION", "ATTEND_SESSION_CONFIRM");
        payload.setOther("sessionId", session.getSessionId());
        quickReply.setPayload(gson.toJson(payload));
        quickReplies.add(quickReply);

        //No
        quickReply = new QuickReply("Nope :(");
        payload = new Payload("LIST_SESSION", "NONE");
        payload.setOther("sessionId", session.getSessionId());
        quickReply.setPayload(gson.toJson(payload));
        quickReplies.add(quickReply);

        textMessage = new TextMessage(this.ATTEND_MSG, quickReplies);
        textMessage.setRecipient(user.getSenderId());

        return textMessage;
    }

    private TextMessage leaveSession(User user, Session session) {
        textMessage = new TextMessage();

        return textMessage;
    }
}
