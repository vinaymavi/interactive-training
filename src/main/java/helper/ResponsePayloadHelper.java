package helper;

import com.google.gson.Gson;
import send.components.ResponsePayload;

/**
 * Created by vku131 on 2/2/18.
 */
public class ResponsePayloadHelper {
    private static final Gson gson = new Gson();
    public static String startQuizPayLoad(String quizId){
        ResponsePayload payload = new ResponsePayload("START_QUIZ", "NONE");
        payload.setOther("quizId", quizId);
        return gson.toJson(payload);
    }
}
