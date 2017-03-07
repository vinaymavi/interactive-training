package helper;

import com.google.gson.Gson;
import entity.*;
import persist.*;
import send.*;
import send.payload.Payload;

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
    private static UserHelper userHelper = new UserHelper();
    AppHelper appHelper = new AppHelper();
    private Payload payload;

    Map<String, Object> other;
    String quizId;
    Quiz quiz;
    List<Question> questionList;
    TextMessage textMessage;
    List<TextMessage> textMessageList;
    List<Session> sessions;
    Answer answer;
    User user;
    Question question;
    Boolean isRight;
    String questionNature;
    String sessionId;
    int questionIndex;
    Session session;
    Presentation presentation;
    Option option;
    List<User> audience;
    Double index;

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
                sessions = SessionOfy.upcomingSessions();
                textMessageList = sessionHelper.sessions(sessions, this.payload.getSenderId());
                facebook.sendMessage(textMessageList);
                logger.warning("List sessions.");
                break;
            case "LIST_CURRENT_SESSION":
                sessions = SessionOfy.currentSessions();
                textMessageList = sessionHelper.sessions(sessions, this.payload.getSenderId());
                facebook.sendMessage(textMessageList);
                logger.warning("List current session.");
                break;
            case "HELP":
                textMessage = appHelper.help(this.payload.getSenderId());
                facebook.sendMessage(gson.toJson(textMessage));
                logger.warning("List help.");
                break;
            case "LIST_MY_SESSION":
                logger.warning("List my session.");
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                List<Presentation> presentations = PresentationOfy.listByUser(user);
                sessions = SessionOfy.loadByPresentation(presentations);
                List<TextMessage> textMessages = sessionHelper.textMessages(sessions, this.payload.getSenderId());
                facebook.sendMessage(textMessages);
                break;
            case "SESSION_QUESTION_GROUPS":
                other = this.payload.getOther();
                sessionId = (String) other.get("sessionId");
                session = SessionOfy.loadBySessionId(sessionId);
                presentation = session.getPresentationRef();
                questionList = QuestionOfy.listByPresentation(presentation);
                textMessage = QuestionHelper.textMessages(questionList, this.payload.getSenderId(), session);
                facebook.sendMessage(gson.toJson(textMessage));
                logger.info("SHOW SESSION_QUESTION_GROUPS");
                break;
            case "SEND_QUESTIONS_TO_AUDIENCE":
                //TODO need to write a server this operation can be long.
                other = this.payload.getOther();
                sessionId = (String) other.get("sessionId");
                session = SessionOfy.loadBySessionId(sessionId);
                audience = session.getAudience();
                presentation = session.getPresentationRef();
                questionNature = (String) other.get("questionNature");
                if (questionNature.equals("feedback")) {
                    questionList = QuestionOfy.feedbackListByPresentation(presentation);
                    for (User u : audience) {
                        textMessageList = QuestionHelper.textMsgFeedback(questionList.get(0), 0, u.getSenderId(), session);
                        for (int i = 0; i < textMessageList.size(); i++) {
                            textMessage = textMessageList.get(i);
                            facebook.sendMessage(gson.toJson(textMessage));
                        }
                    }
                } else {
                    questionList = QuestionOfy.questionListByPresentation(presentation);
                    for (User u : audience) {
                        textMessageList = QuestionHelper.textMsgQuestion(questionList.get(0), 0, u.getSenderId(), session);
                        for (int i = 0; i < textMessageList.size(); i++) {
                            textMessage = textMessageList.get(i);
                            facebook.sendMessage(gson.toJson(textMessage));
                        }
                    }
                }
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
                //TODO this is duplicate.
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
//                TODO need to add selected option.
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
            case "SESSION_ACTIONS":
                other = this.payload.getOther();
                sessionId = (String) other.get("sessionId");
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                session = SessionOfy.loadBySessionId(sessionId);
                if (session == null) {
                    logger.warning("Session null sessionId=" + sessionId);
                }
                textMessage = sessionHelper.sessionActions(user, session);
                facebook.sendMessage(textMessage);
                logger.info("SESSION_ACTIONS");
                break;
            case "JOIN_SESSION":
            case "ATTEND_SESSION":
                other = this.payload.getOther();
                sessionId = (String) other.get("sessionId");
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                session = SessionOfy.loadBySessionId(sessionId);
                sessionHelper.addAudience(user, session);
                userHelper.joinSession(user, session);
                break;
            case "ADD_FEEDBACK_ANSWER":
//                TODO code looks duplicate by next section.
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                other = this.payload.getOther();
                question = QuestionOfy.loadByQuestionId((String) this.payload.getOther().get("questionId"));
                index = (Double) other.get("optionIndex");
                option = question.getOptions().get(index.intValue());
                answer = new Answer(user, question, option);
                sessionId = (String) other.get("sessionId");
                session = SessionOfy.loadBySessionId(sessionId);
                answer.setSessionRef(session);
                logger.info("Answer Key = " + AnswerOfy.save(answer));
                break;
            case "ADD_QUESTION_ANSWER":
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                other = this.payload.getOther();
                question = QuestionOfy.loadByQuestionId((String) this.payload.getOther().get("questionId"));
                index = (Double) other.get("optionIndex");
                option = question.getOptions().get(index.intValue());
                isRight = (Boolean) other.get("isRight");
                answer = new Answer(user, question, option);
                answer.setRight(isRight);
                sessionId = (String) other.get("sessionId");
                session = SessionOfy.loadBySessionId(sessionId);
                answer.setSessionRef(session);
                logger.info("Answer Key = " + AnswerOfy.save(answer));
                break;
            default:
                logger.warning("un-known action");
        }
    }

    private void processNextAction() {
        logger.warning("Next Action = " + this.payload.getNextAction());
        QuizHelper quizHelper = new QuizHelper();
        Double index;
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
                index = (Double) other.get("questionIndex");
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
                /**
                 * Send message to session owner.
                 */
                logger.info("SEND_CONFIRMATION_MSG_TO_OWNER");
                break;
            case "JOIN_SESSION_CONFIRM":
            case "ATTEND_SESSION_CONFIRM":
                /**
                 * User register for session confirmation.
                 */
                other = this.payload.getOther();
                sessionId = (String) other.get("sessionId");
                user = UserOfy.loadBySenderId(this.payload.getSenderId());
                session = SessionOfy.loadBySessionId(sessionId);
                textMessage = sessionHelper.registrationSuccessful(user);
                facebook.sendMessage(gson.toJson(textMessage));
                break;
            case "SEND_NEXT_FEEDBACK_QUESTION":
                other = this.payload.getOther();
                sessionId = (String) other.get("sessionId");
                session = SessionOfy.loadBySessionId(sessionId);
                presentation = session.getPresentationRef();
                questionList = QuestionOfy.feedbackListByPresentation(presentation);
                index = (Double) other.get("questionIndex");
                questionIndex = index.intValue() + 1;
                if (questionList.size() == questionIndex) {
                    textMessageList = QuestionHelper.feedbackEndMsg(this.payload.getSenderId());
                } else {
                    textMessageList = QuestionHelper.textMsgFeedback(questionList.get(questionIndex), questionIndex, this.payload.getSenderId(), session);
                }
                for (int i = 0; i < textMessageList.size(); i++) {
                    textMessage = textMessageList.get(i);
                    facebook.sendMessage(gson.toJson(textMessage));
                }
                break;
            case "SEND_NEXT_QUESTION_QUESTION":
                other = this.payload.getOther();
                sessionId = (String) other.get("sessionId");
                session = SessionOfy.loadBySessionId(sessionId);
                presentation = session.getPresentationRef();
                questionList = QuestionOfy.questionListByPresentation(presentation);
                index = (Double) other.get("questionIndex");
                questionIndex = index.intValue() + 1;
                if (questionList.size() == questionIndex) {
                    textMessageList = QuestionHelper.feedbackEndMsg(this.payload.getSenderId());
                } else {
                    textMessageList = QuestionHelper.textMsgQuestion(questionList.get(questionIndex), questionIndex, this.payload.getSenderId(), session);
                }
                for (int i = 0; i < textMessageList.size(); i++) {
                    textMessage = textMessageList.get(i);
                    facebook.sendMessage(gson.toJson(textMessage));
                }
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
