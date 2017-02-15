package helper;

import com.google.gson.Gson;
import entity.Option;
import entity.Question;
import entity.User;
import send.TextMessage;

import java.util.*;

/**
 * Created by vku131 on 1/25/17.
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

}
