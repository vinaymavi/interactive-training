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
    private static final String ADMIN_PAYLOAD = "ADMIN_MESSAGE";
    private String from;
    private String action;
    private String status;
    private String nextAction;
    private String senderId;

    public PayloadHelper(String[] payloadItems) {
//        "payload":"ADMIN_MESSAGE:REGISTRATION:SUCCESS:SEND_MESSAGE:null"
        if (payloadItems != null && payloadItems.length >= 5) {
            this.from = payloadItems[0];
            this.action = payloadItems[1];
            this.status = payloadItems[2];
            this.nextAction = payloadItems[3];
            this.senderId = payloadItems[4];
        } else {
            logger.warning("payload is null or not complete");
        }
    }

    public void processPayload() {
        switch (this.from) {
            case PayloadHelper.ADMIN_PAYLOAD:
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
                User user = UserOfy.loadBySenderId(this.senderId);
                user.setRegistered(true);
                UserOfy.save(user);
                break;
            default:
                logger.warning("un-known action");
        }
    }

    private void processNextAction() {
        logger.warning("Next Action = "+this.nextAction);
        switch (this.nextAction) {
            case "SEND_WELCOME_MESSAGE":
                User user = UserOfy.loadBySenderId(this.senderId);
                String welcomeStr = ConversationMessage.welcomeMessage(user, this.senderId);
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
