package helper;

import com.google.gson.Gson;
import config.ActionNames;
import entity.webhook.facebook.FbUserProfile;
import entity.webhook.facebook.MessageEntry;
import send.components.ResponsePayload;

/**
 * Created by vku131 on 2/2/18.
 */
public class ResponsePayloadHelper {
    private static final Gson gson = new Gson();

    public static String startQuiz(String quizId) {
        ResponsePayload payload = new ResponsePayload(ActionNames.START_QUIZ, ActionNames.NONE);
        payload.setOther("quizId", quizId);
        return gson.toJson(payload);
    }

    public static String adminMessageAddUser(FbUserProfile fbUserProfile, MessageEntry messageEntry) {
        ResponsePayload payload = new ResponsePayload(ActionNames.REGISTRATION, ActionNames.SEND_WELCOME_MESSAGE,messageEntry.getSender().get("id"));
        payload.setOther("firstName", fbUserProfile.getFirst_name());
        payload.setOther("lastName", fbUserProfile.getLast_name());
        payload.setOther("gender", fbUserProfile.getGender());
        payload.setOther("profileImage", fbUserProfile.getProfile_pic());
        payload.setMessengerId(messageEntry.getSender().get("id"));
        return gson.toJson(payload);
    }
    public static String adminMessageWrongInfo(){
        ResponsePayload payload = new ResponsePayload(ActionNames.NONE,ActionNames.NONE);
        return gson.toJson(payload);
    }

    public static String quizInfo(String quizId){
        ResponsePayload payload = new ResponsePayload(ActionNames.QUIZ_INFO,ActionNames.NONE);
        payload.setOther("quizId",quizId);
        return gson.toJson(payload);
    }

}
