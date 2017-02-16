package helper;

import entity.Option;
import entity.User;
import entity.webhook.facebook.FbUserProfile;
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

    public static List<QuickReply> optionToQuickReply(List<Option> optionList) {
        List<QuickReply> quickRepliesList = new ArrayList<>();
        QuickReply qr;
        for (Option option : optionList) {
            qr = new QuickReply("text", option.getContent());
            qr.setPayload("QUESTION NUMEBR");
            quickRepliesList.add(qr);
        }
        return quickRepliesList;
    }

    public static User UserProfileToUser(FbUserProfile fbUserProfile, User user, String senderId) {
        if (!(user instanceof User)) {
            user = new User();
        }
        user.setFirstName(fbUserProfile.getFirst_name());
        user.setLastName(fbUserProfile.getLast_name());
        user.setSenderId(senderId);
        return user;
    }
}
