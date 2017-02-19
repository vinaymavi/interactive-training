package helper;

import com.google.gson.Gson;
import entity.Quiz;
import entity.User;
import persist.QuizOfy;
import persist.UserOfy;
import send.ConversationMessage;
import send.Facebook;
import send.QuickReply;
import send.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/15/17.
 */
public class PayloadHelper {
    private static final String QUIZ_LIST_MESSAGE = "Please select quiz from bubble list.";
    private static Logger logger = Logger.getLogger(PayloadHelper.class.getName());
    private static final Gson gson = new Gson();
    private static final Facebook facebook = new Facebook();
    private String from;
    private String action;
    private String status;
    private String nextAction;
    private String messengerId;
    private String senderId;

    public PayloadHelper(String[] payloadItems, String senderId) {
//        TODO payload should be a class.
//        "payload":"ADMIN_MESSAGE:REGISTRATION:SUCCESS:SEND_MESSAGE:null"
        if (payloadItems != null && payloadItems.length >= 5) {
            this.from = payloadItems[0];
            this.action = payloadItems[1];
            this.status = payloadItems[2];
            this.nextAction = payloadItems[3];
            this.messengerId = payloadItems[4];
            this.senderId = senderId;
        } else {
            logger.warning("payload is null or not complete");
        }
    }

    public void processPayload() {
        switch (this.from) {
            case "ADMIN_MESSAGE":
                this.processAction();
                this.processNextAction();
                break;
            default:
                logger.warning("this is an un-known payload.");
        }
    }

    private void processAction() {
        switch (this.action) {
            case "REGISTRATION":
                User user = UserOfy.loadBySenderId(this.messengerId);
                user.setRegistered(true);
                UserOfy.save(user);
                break;
            case "LIST_QUIZ":
//                TODO move this code quiz helper.
                List<Quiz> quizList = QuizOfy.list();
                if(quizList.size() >0){
                    QuizHelper quizHelper = new QuizHelper(quizList);
                    String msgPayload;
                    TextMessage textMessage = new TextMessage(QUIZ_LIST_MESSAGE, quizHelper.quickReplies(true));
                    textMessage.setRecipient(senderId);
                    msgPayload = gson.toJson(textMessage);
                    logger.info("message = "+ msgPayload);
                    facebook.sendMessage(msgPayload);
                }else{
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
            default:
                logger.warning("un-known action");
        }
    }

    private void processNextAction() {
        logger.warning("Next Action = " + this.nextAction);
        switch (this.nextAction) {
            case "SEND_WELCOME_MESSAGE":
                User user = UserOfy.loadBySenderId(this.messengerId);
                String welcomeStr = ConversationMessage.welcomeMessage(user, this.messengerId);
                Facebook facebook = new Facebook();
                facebook.sendMessage(welcomeStr);
                break;
            case "NONE":
                logger.warning("No Action required.");
                break;
            default:
                logger.warning("un-known next action");
        }
    }
}
