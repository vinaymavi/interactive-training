package helper;

import com.google.gson.Gson;
import entity.*;
import send.QuickReply;
import send.TextMessage;
import send.payload.Payload;

import java.util.*;

/**
 * Question entity helper functions.
 */
public class QuestionHelper {
    private static Gson gson = new Gson();

    public static Option getRightOptions(List<Option> optionList) {
        Option rightOption = null;
        for (Option option : optionList) {
            if (option.isRight()) {
                rightOption = option;
            }
        }
        return rightOption;
    }

    public static List<Option> createFeedbackOptions(String questionType) {
        List<Option> optionList = new ArrayList<>();
        switch (questionType) {
            case "yesno":
                optionList.add(new Option("yes"));
                optionList.add(new Option("no"));
                break;
            default:
                optionList.add(new Option("1"));
                optionList.add(new Option("2"));
                optionList.add(new Option("3"));
                optionList.add(new Option("4"));
                optionList.add(new Option("5"));
        }
        return optionList;
    }

    @Deprecated
    public static String createFbTextMsg(User user, Question question) {
        Map<String, String> recipient = new HashMap<>();
        Map<String, Object> msgMap = new HashMap<>();
        TextMessage textMessage = new TextMessage();
        textMessage.setRecipient(user.getSenderId());
        msgMap.put("text", question.getDesc());
        msgMap.put("quick_replies", FacebookHelper.optionToQuickReply(question.getOptions()));
        textMessage.setMessage(msgMap);
        return gson.toJson(textMessage);
    }

    /**
     * Return text message for a question.
     *
     * @param question      {Question}
     * @param questionIndex {Int} list index of question.
     * @param senderId      {String}
     * @return
     */
    public static List<TextMessage> textMessage(Question question, Quiz quiz, int questionIndex, String senderId) {
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply;
        Payload payload;
        StringBuffer messageContent = new StringBuffer();

        List<Option> options = question.getOptions();
        Option option;
        TextMessage textMessage;
        List<TextMessage> textMessageList = new ArrayList<>();

        // Question textMessage.
        messageContent.append(question.getDesc());
        messageContent.append(System.lineSeparator());
        messageContent.append(System.lineSeparator());

        // Option text message.
        for (int i = 0; i < options.size(); i++) {
            option = options.get(i);
            quickReply = new QuickReply(" " + (i + 1) + " ");
            payload = new Payload("ADD_ANSWER", "SEND_NEXT_QUESTION");
            payload.setOther("questionId", question.getQuestionId());
            payload.setOther("isRight", option.isRight());
            payload.setOther("questionIndex", questionIndex);
            payload.setOther("quizId", quiz.getQuizId());
            quickReply.setPayload(gson.toJson(payload));
            quickReplies.add(quickReply);
            messageContent.append("#" + (i + 1) + "    " + option.getContent());
            messageContent.append(System.lineSeparator());
        }
        textMessage = new TextMessage(messageContent.toString(),quickReplies);
        textMessage.setRecipient(senderId);
        textMessageList.add(textMessage);

        return textMessageList;
    }

    /**
     * Return text message with question group payload.
     *
     * @param questions {{@link List<Question>}}
     * @param senderId  {{@link String}}
     * @return
     */
    public static TextMessage textMessage(List<Question> questions, String senderId, Session session) {
        TextMessage textMessage;
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply;
        Payload payload;
        Set groups = questionGroups(questions);
        Iterator iterator = groups.iterator();
        Question question = questions.get(0);
        String questionNature;
        while (iterator.hasNext()) {
            questionNature = (String) iterator.next();
            quickReply = new QuickReply(questionNature);
            payload = new Payload("SEND_QUESTIONS_TO_AUDIENCE", "SEND_CONFIRMATION_MSG_TO_OWNER");
            payload.setOther("presentationId", question.getPresentationRef().getPresentationId());
            payload.setOther("questionNature", questionNature);
            payload.setOther("sessionId", session.getSessionId());
            quickReply.setPayload(gson.toJson(payload));
            quickReplies.add(quickReply);
        }
        textMessage = new TextMessage("Please select group to send question to audience.", quickReplies);
        textMessage.setRecipient(senderId);
        return textMessage;
    }

    /**
     * Create message for question nature feedback.
     *
     * @param question      {{@link Question}}
     * @param questionIndex {{@link int}}
     * @param senderId      {{@link String}}
     * @param session       {{@link Session}}
     * @return {{@link List<TextMessage>}}
     */
    public static List<TextMessage> textMsgFeedback(Question question, int questionIndex, String senderId, Session session) {
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply;
        Payload payload;

        List<Option> options = question.getOptions();
        Option option;
        TextMessage textMessage;
        List<TextMessage> textMessageList = new ArrayList<>();

        // Question textMessage.
        textMessage = new TextMessage(question.getDesc());
        textMessageList.add(textMessage);
        textMessage.setRecipient(senderId);

        // Option text message.
        for (int i = 0; i < options.size(); i++) {
            option = options.get(i);
            quickReply = new QuickReply(" " + (i + 1) + " ");
            payload = new Payload("ADD_FEEDBACK_ANSWER", "SEND_NEXT_FEEDBACK_QUESTION");
            payload.setOther("questionId", question.getQuestionId());
            payload.setOther("questionIndex", questionIndex);
            payload.setOther("optionIndex", i);
            payload.setOther("sessionId", session.getSessionId());
            quickReply.setPayload(gson.toJson(payload));
            quickReplies.add(quickReply);
            textMessage = new TextMessage("#" + (i + 1) + "    " + option.getContent(), quickReplies);
            textMessage.setRecipient(senderId);
            textMessageList.add(textMessage);
        }
        return textMessageList;
    }

    /**
     * Create question for questionNature question.
     *
     * @param question      {{@link Question}}
     * @param questionIndex {{@link int}}
     * @param senderId      {{@link String}}
     * @param session       {{@link Session}}
     * @return {{@link List<TextMessage>}}
     */
    public static List<TextMessage> textMsgQuestion(Question question, int questionIndex, String senderId, Session session) {
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply;
        Payload payload;
        StringBuffer messageContent = new StringBuffer();

        List<Option> options = question.getOptions();
        Option option;
        TextMessage textMessage;
        List<TextMessage> textMessageList = new ArrayList<>();

        // Question textMessage.
        messageContent.append(question.getDesc());

        // Option text message.
        for (int i = 0; i < options.size(); i++) {
            option = options.get(i);
            quickReply = new QuickReply(" " + (i + 1) + " ");
            payload = new Payload("ADD_QUESTION_ANSWER", "SEND_NEXT_QUESTION_QUESTION");
            payload.setOther("questionId", question.getQuestionId());
            payload.setOther("questionIndex", questionIndex);
            payload.setOther("isRight", option.isRight());
            payload.setOther("optionIndex", i);
            payload.setOther("sessionId", session.getSessionId());
            quickReply.setPayload(gson.toJson(payload));
            quickReplies.add(quickReply);
            messageContent.append("#" + (i + 1) + "    " + option.getContent());
        }
        textMessage = new TextMessage(messageContent.toString(),quickReplies);
        textMessage.setRecipient(senderId);
        textMessageList.add(textMessage);
        return textMessageList;
    }

    public static List<TextMessage> feedbackEndMsg(String senderId) {
        List<TextMessage> textMessageList = new ArrayList<>();
        TextMessage textMessage = new TextMessage("Thank you for your feedback.");
        textMessage.setRecipient(senderId);
        textMessageList.add(textMessage);
        return textMessageList;
    }

    private static Set<String> questionGroups(List<Question> questions) {
        Set<String> groups = new HashSet<>();
        for (Question question : questions) {
            groups.add(question.getQuestionNature());
        }
        return groups;
    }
}
