package helper;

import com.google.gson.Gson;
import entity.*;
import persist.*;
import send.*;
import send.payload.Payload;

import javax.xml.soap.Text;
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
    private static SessionHelper sessionHelper = new SessionHelper();
    private Payload payload;

    Map<String, Object> other;
    String quizId;
    Quiz quiz;
    List<Question> questionList;
    TextMessage textMessage;
    List<TextMessage> textMessageList;
    Answer answer;
    User user;
    Question question;
    Boolean isRight;
    String questionNature;
    String sessionId;
    int questionIndex;
    Session session;
    Presentation presentation;

    public PayloadHelper(Payload payload) {
        this.payload = payload;
    }

    public void processPayload() {
        this.sendEditAction(this.payload.getSenderId());
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
        switch (this.payload.getAction()) {
            case "REGISTRATION":
                user = UserOfy.loadBySenderId(this.payload.getMessengerId());
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
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                List<Presentation> presentations = PresentationOfy.listByUser(user);
                List<Session> sessions = SessionOfy.loadByPresentation(presentations);
                List<TextMessage> textMessages = sessionHelper.textMessages(sessions, this.payload.getSenderId());
                for (TextMessage textMessage : textMessages) {
                    facebook.sendMessage(gson.toJson(textMessage));
                }
                break;
            case "SESSION_QUESTION_GROUPS":
                other = this.payload.getOther();
                sessionId = (String) other.get("sessionId");
                session = SessionOfy.loadBySessionId(sessionId);
                presentation = session.getPresentationRef();
                questionList = QuestionOfy.listByPresentation(presentation);
                textMessage = QuestionHelper.textMessages(questionList, this.payload.getSenderId());
                facebook.sendMessage(gson.toJson(textMessage));
                logger.info("SHOW SESSION_QUESTION_GROUPS");
                break;
            case "SEND_QUESTIONS_TO_AUDIENCE":
                logger.info("SEND_QUESTIONS_TO_AUDIENCE");
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
//                TODO this is duplicate.
                Map<String, Object> other = this.payload.getOther();
                quizId = (String) other.get("quizId");
                quiz = QuizOfy.loadById(quizId);
                questionList = QuestionOfy.questionListByQuiz(quiz);
                textMessageList = QuestionHelper.textMessage(questionList.get(0), quiz, 0, this.payload
                        .getSenderId());
                for (int i = 0; i < textMessageList.size(); i++) {
                    textMessage = textMessageList.get(i);
                    facebook.sendMessage(gson.toJson(textMessage));
                }

                break;
            case "ADD_ANSWER":
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                other = this.payload.getOther();
                question = QuestionOfy.loadByQuestionId((String) this.payload.getOther().get("questionId"));
                isRight = (Boolean) this.payload.getOther().get("isRight");
                answer = new Answer(user, question, isRight);
                quizId = (String) other.get("quizId");
                quiz = QuizOfy.loadById(quizId);
                answer.setQuizRef(quiz);
                logger.info("Answer Key = " + AnswerOfy.save(answer));
                break;
            default:
                logger.warning("un-known action");
        }
    }

    private void processNextAction() {
        logger.warning("Next Action = " + this.payload.getNextAction());
        QuizHelper quizHelper = new QuizHelper();
        switch (this.payload.getNextAction()) {
            case "SEND_WELCOME_MESSAGE":
                user = UserOfy.loadBySenderId(this.payload.getMessengerId());
                String welcomeStr = ConversationMessage.welcomeMessage(user, this.payload.getMessengerId());
                facebook.sendMessage(welcomeStr);
                break;
            case "SEND_NEXT_QUESTION":
                //TODO this is duplicate code. need to write a single place.
                other = this.payload.getOther();
                quizId = (String) other.get("quizId");
                quiz = QuizOfy.loadById(quizId);
                questionList = QuestionOfy.questionListByQuiz(quiz);
                Double index = (Double) other.get("questionIndex");
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                questionIndex = index.intValue() + 1;
                if (questionIndex == questionList.size()) {
                    logger.info("No more question need to send result");

                    textMessage = quizHelper.quizCompleteMsg(this.payload.getSenderId());
                    facebook.sendMessage(gson.toJson(textMessage));
                    Map<String, String> resultMap = quizHelper.quizResult(quiz, user);
                    if (resultMap != null) {
                        textMessage = quizHelper.resultMessage(resultMap.get("result"), this.payload.getSenderId());
                        facebook.sendMessage(gson.toJson(textMessage));
                    }

                } else {
                    textMessageList = QuestionHelper.textMessage(questionList.get(questionIndex), quiz, questionIndex, this.payload
                            .getSenderId());
                    for (int i = 0; i < textMessageList.size(); i++) {
                        textMessage = textMessageList.get(i);
                        facebook.sendMessage(gson.toJson(textMessage));
                    }
                }
                break;
            case "SEND_CONFIRMATION_MSG_TO_OWNER":
                logger.info("SEND_CONFIRMATION_MSG_TO_OWNER");
                break;
            case "NONE":
                logger.warning("No Action required.");
                break;
            default:
                logger.warning("un-known next action");
        }
    }

    private void sendEditAction(String senderId) {
        ActionTyping actionTyping = new ActionTyping();
        actionTyping.setRecipient(senderId);
        String msg = gson.toJson(actionTyping);
        logger.info("ActionTyping =" + msg);
        facebook.sendMessage(msg);
    }
}
