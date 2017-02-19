package helper;

import com.google.gson.Gson;
import entity.Option;
import entity.Question;
import entity.User;
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
     * @param question      {Question}
     * @param questionIndex {Int} list index of question.
     * @param senderId      {String}
     * @return
     */
    public static TextMessage textMessage(Question question, int questionIndex, String senderId) {
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply;
        Payload payload;

        List<Option> options = question.getOptions();
        Option option;
        for (int i = 0; i < options.size(); i++) {
            option = options.get(i);
            quickReply = new QuickReply(option.getContent());
            payload = new Payload("ADD_ANSWER", "SEND_NEXT_QUESTION");
            payload.setOther("questionId", question.getQuestionId());
            payload.setOther("isRight", option.isRight());
            payload.setOther("questionIndex", questionIndex);
            quickReply.setPayload(gson.toJson(payload));
            quickReplies.add(quickReply);
        }

        TextMessage textMessage = new TextMessage(question.getDesc(), quickReplies);
        textMessage.setRecipient(senderId);
        return textMessage;
    }
}
