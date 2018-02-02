package helper;

import api.Constants;
import com.google.gson.Gson;
import entity.Quiz;
import send.components.ResponsePayload;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/3/18.
 */
public class ShortUrlHelper {
    private static Logger logger = Logger.getLogger(ShortUrlHelper.class.getName());
    private static final Gson gson = new Gson();
    public static URL quiz(Quiz quiz) {
        String payload = ResponsePayloadHelper.quizInfo(quiz.getQuizId());
        URL url = null;
        try {
            url = new URL(Constants.BOT_SHORT_URL + gson.toJson(payload));
        }catch (MalformedURLException e){
            logger.warning(e.getMessage());
        }
        return url;
    }
}
