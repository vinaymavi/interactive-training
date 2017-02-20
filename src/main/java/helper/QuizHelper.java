package helper;

import com.google.gson.Gson;
import entity.Quiz;
import send.QuickReply;
import send.TextMessage;
import send.payload.Payload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinaymavi on 19/02/17.
 */
public class QuizHelper {
    private static final String QUIZ_COMPLETE_MSG = "Hey, you have been successfully completed quiz :)." +
            "" + System.lineSeparator() + "We will send quiz result shortly.";
    private static final Gson gson = new Gson();
    private Quiz quiz;
    private List<Quiz> quizs;
    Payload payload;

    public QuizHelper() {
    }

    public QuizHelper(Quiz quiz) {
        this.quiz = quiz;
    }

    public QuizHelper(List<Quiz> quizs) {
        this.quizs = quizs;
    }

    /**
     * return quick reply list for single quiz.
     *
     * @param isList {{Boolean}}
     * @return List{{QuickReply}}
     */
    public List<QuickReply> quickReplies(Boolean isList) {
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply;
        for (Quiz quiz : this.quizs) {
            quickReply = new QuickReply(quiz.getName());
            payload = new Payload("QUIZ_INFO", "NONE");
            payload.setOther("quizId", quiz.getQuizId());
            quickReply.setPayload(gson.toJson(payload));
            quickReplies.add(quickReply);
        }
        return quickReplies;
    }

    /**
     * return quick reply list for single quiz.
     *
     * @return List{{QuickReply}}
     */
    public List<QuickReply> quickReplies() {
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply = new QuickReply(this.quiz.getName());
        payload = new Payload("QUIZ_INFO", "NONE");
        payload.setOther("quizId", quiz.getQuizId());
        quickReply.setPayload(gson.toJson(payload));
        quickReplies.add(quickReply);
        return quickReplies;
    }

    public TextMessage quizInfo(String senderId) {
        List<QuickReply> quickReplies = new ArrayList<>();
        QuickReply quickReply;
        Payload payload;
        String msg = "Name = " + this.quiz.getName() + ", Desc = " + this.quiz.getDesc();
        quickReply = new QuickReply("Start");
        payload = new Payload("START_QUIZ", "NONE");
        payload.setOther("quizId", this.quiz.getQuizId());
        quickReply.setPayload(gson.toJson(payload));
        quickReplies.add(quickReply);

        quickReply = new QuickReply("Back");
        payload = new Payload("LIST_QUIZ", "NONE");
        quickReply.setPayload(gson.toJson(payload));
        quickReplies.add(quickReply);

        TextMessage textMessage = new TextMessage(msg, quickReplies);
        textMessage.setRecipient(senderId);
        return textMessage;
    }

    public TextMessage quizCompleteMsg(String senderId) {
        TextMessage textMessage = new TextMessage(QUIZ_COMPLETE_MSG);
        textMessage.setRecipient(senderId);
        return textMessage;
    }
}
