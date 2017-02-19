package helper;

import com.google.gson.Gson;
import entity.Question;
import entity.Quiz;
import entity.User;
import persist.QuestionOfy;
import persist.QuizOfy;
import persist.UserOfy;
import send.ConversationMessage;
import send.Facebook;
import send.QuickReply;
import send.TextMessage;
import send.payload.Payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Payload helper main class the process all webhook requests payload.
 */
public class PayloadHelper {
    private static final String QUIZ_LIST_MESSAGE = "Please select quiz from bubble list.";
    private static Logger logger = Logger.getLogger(PayloadHelper.class.getName());
    private static final Gson gson = new Gson();
    private static final Facebook facebook = new Facebook();
    private Payload payload;

    public PayloadHelper(Payload payload) {
        this.payload = payload;
    }

    public void processPayload() {
        switch (this.payload.getFrom()) {
            case "ADMIN_MESSAGE":
                this.processAction();
                this.processNextAction();
                break;
            default:
                logger.warning("this is an un-known payload.");
        }
    }

    private void processAction() {
        String quizId;
        Quiz quiz;
        List<Question> questionList;
        TextMessage textMessage;
        switch (this.payload.getAction()) {
            case "REGISTRATION":
                User user = UserOfy.loadBySenderId(this.payload.getMessengerId());
                user.setRegistered(true);
                UserOfy.save(user);
                break;
            case "LIST_QUIZ":
//                TODO move this code quiz helper.
                List<Quiz> quizList = QuizOfy.list();
                if (quizList.size() > 0) {
                    QuizHelper quizHelper = new QuizHelper(quizList);
                    String msgPayload;
                    textMessage = new TextMessage(QUIZ_LIST_MESSAGE, quizHelper.quickReplies(true));
                    textMessage.setRecipient(this.payload.getSenderId());
                    msgPayload = gson.toJson(textMessage);
                    logger.info("message = " + msgPayload);
                    facebook.sendMessage(msgPayload);
                } else {
                    logger.warning("No quiz found");
                }
                break;
            case "LIST_SESSION":
                logger.warning("List sessions.");
                break;
            case "LIST_CURRENT_SESSION":
                logger.warning("List current session.");
                break;
            case "HELP":
                logger.warning("List help.");
                break;
            case "LIST_MY_SESSION":
                logger.warning("List my session.");
                break;
            case "QUIZ_INFO":
                logger.warning("Send Quiz info");
                quizId = (String) this.payload.getOther().get("quizId");
                quiz = QuizOfy.loadById(quizId);
                QuizHelper quizHelper = new QuizHelper(quiz);
                textMessage = quizHelper.quizInfo(this.payload.getSenderId());
                facebook.sendMessage(gson.toJson(textMessage));
                break;
            case "START_QUIZ":
                Map<String, Object> other = this.payload.getOther();
                quizId = (String) other.get("quizId");
                quiz = QuizOfy.loadById(quizId);
                questionList = QuestionOfy.questionListByQuiz(quiz);
                textMessage = QuestionHelper.textMessage(questionList.get(0), 0, this.payload.getSenderId());
                facebook.sendMessage(gson.toJson(textMessage));
                break;
            case "ADD_ANSWER":
                logger.warning("add answer");
                break;
            default:
                logger.warning("un-known action");
        }
    }

    private void processNextAction() {
        logger.warning("Next Action = " + this.payload.getNextAction());
        switch (this.payload.getNextAction()) {
            case "SEND_WELCOME_MESSAGE":
                User user = UserOfy.loadBySenderId(this.payload.getMessengerId());
                String welcomeStr = ConversationMessage.welcomeMessage(user, this.payload.getMessengerId());
                Facebook facebook = new Facebook();
                facebook.sendMessage(welcomeStr);
                break;
            case "SEND_NEXT_QUESTION":
                logger.warning("SEND_NEXT_QUESTION to user");
                break;
            case "NONE":
                logger.warning("No Action required.");
                break;
            default:
                logger.warning("un-known next action");
        }
    }
}
