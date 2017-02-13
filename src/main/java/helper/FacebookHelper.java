package helper;

import send.QuickReply;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vku131 on 2/13/17.
 */
public class FacebookHelper {
    public static List<QuickReply> StringToQuickReplies(String quickReplies) {
        String[] quickRepliyArr = quickReplies.split(",");
        List<QuickReply> quickRepliesList = new ArrayList<>();
        QuickReply qr;
        for (String quickReply : quickRepliyArr) {
            qr = new QuickReply("text", quickReply);
            qr.setPayload("QUESTION NUMEBR");
            quickRepliesList.add(qr);

        }
        return quickRepliesList;
    }
}
