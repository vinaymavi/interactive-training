package helper;

import entity.User;
import persist.UserOfy;
import send.ConversationMessage;
import send.Facebook;

import java.util.logging.Logger;

/**
 * Created by vku131 on 2/15/17.
 */
public class PayloadHelper {

    private static Logger logger = Logger.getLogger(PayloadHelper.class.getName());
    private String from;
    private String action;
    private String status;
    private String nextAction;
    private String messengerId;
    private String senderId;

    public PayloadHelper(String[] payloadItems) {
//        "payload":"ADMIN_MESSAGE:REGISTRATION:SUCCESS:SEND_MESSAGE:NONE"
        if (payloadItems != null || payloadItems.length >= 5) {
            this.from = payloadItems[0];
            this.action = payloadItems[1];
            this.status = payloadItems[2];
            this.nextAction = payloadItems[3];
            this.messengerId = payloadItems[4];
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
                logger.warning("List quiz.");
                break;
            case "LIST_SESSION":
                logger.warning("List session.");
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
        switch (nextAction) {
            case "SEND_WELCOME_MESSAGE":
                User user = UserOfy.loadBySenderId(this.messengerId);
                String welcomeStr = ConversationMessage.welcomeMessage(user, this.messengerId);
                Facebook facebook = new Facebook();
                facebook.sendMessage(welcomeStr);
                break;
            default:
                logger.warning("un-known next action");
        }
    }
}
