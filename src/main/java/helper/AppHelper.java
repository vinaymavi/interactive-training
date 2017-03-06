package helper;

import send.TextMessage;

/**
 * This is helper class for application.
 * All application message creation should be here.
 */
public class AppHelper {
    private String HELP_MESSAGE = "Hey, Right now we are helpless to help you :(.";
    private TextMessage textMessage;

    /**
     * @param senderId {{@link String}}
     * @return {{@link TextMessage}}
     */
    public TextMessage help(String senderId) {
        textMessage = new TextMessage(HELP_MESSAGE);
        textMessage.setRecipient(senderId);
        return textMessage;
    }
}
