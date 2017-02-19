package helper;

import com.google.gson.Gson;
import entity.Quiz;
import send.QuickReply;
import send.payload.Payload;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinaymavi on 19/02/17.
 */
public class QuizHelper {
    private static final Gson gson = new Gson();
    private Quiz quiz;
    private List<Quiz> quizs;
    Payload payload;

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
        quickReply.setPayload(gson.toJson(payload));
        quickReplies.add(quickReply);
        return quickReplies;
    }
}
