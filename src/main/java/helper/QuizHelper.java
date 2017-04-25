package helper;

import com.google.gson.Gson;
import entity.Answer;
import entity.Quiz;
import entity.User;
import persist.AnswerOfy;
import send.QuickReply;
import send.TextMessage;
import send.payload.Payload;

import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * This is collection of Quiz entity helper function.
 */
public class QuizHelper {
    private static final Logger logger = Logger.getLogger(QuestionHelper.class.getName());
    private static final String QUIZ_COMPLETE_MSG = "Hey, you have been successfully completed quiz :)." +
            "" + System.lineSeparator() + "We will send quiz result shortly.";
    private static final String SKILL_CATALOGUE = "Hey, Your skills are registered successfully." +
            "" + System.lineSeparator() + "Thank you sparing time with us :)";
    private static final Gson gson = new Gson();
    private Quiz quiz;
    private List<Quiz> quizs;
    DecimalFormat decimalFormat = new DecimalFormat("####0.00");
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

    public TextMessage skillCatalogue(String senderId) {
        TextMessage textMessage = new TextMessage(SKILL_CATALOGUE);
        textMessage.setRecipient(senderId);
        return textMessage;
    }

    /**
     * Create quiz result.
     *
     * @param quiz {{@link Quiz}}
     * @param user {{@link User}}
     * @return {{@link Map}}
     */
    public Map<String, String> quizResult(Quiz quiz, User user) {
        List<Answer> answers = AnswerOfy.loadByQuiz(quiz, user);
        double totalAns = answers.size();
        if (totalAns == 0) {
            logger.warning("No Answer found.");
            return null;
        }
        double rightAns = 0;
        double wrongAns = 0;
        double result;
        Map<String, String> resultMap = new HashMap<>();
        for (Answer ans : answers) {
            if (ans.isRight()) {
                rightAns++;
            }
        }
        wrongAns = totalAns - rightAns;
        logger.info("Total=" + totalAns + ", Right=" + rightAns + ", wrong=" + wrongAns);
        result = ((rightAns / totalAns) * 100);
        resultMap.put("result", "Your score is " + decimalFormat.format(result) + "% .");
        return resultMap;
    }

    /**
     * create result text message.
     *
     * @param msg      {{@link String}}
     * @param senderId {{@link String}}
     * @return {{@link TextMessage}}
     */
    public TextMessage resultMessage(String msg, String senderId) {
        TextMessage textMessage = new TextMessage(msg);
        textMessage.setRecipient(senderId);
        return textMessage;
    }

    public void sendQuizResult() {

    }

    public Set<User> audience(Quiz quiz) {
        List<Answer> answers = AnswerOfy.loadByQuiz(quiz);
        Set<User> auidence = new HashSet<>();
        for (Answer a : answers) {
            auidence.add(a.getUserRef());
        }
        return auidence;
    }
}
