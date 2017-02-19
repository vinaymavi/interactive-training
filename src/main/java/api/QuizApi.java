package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import entity.Quiz;
import entity.User;
import helper.AuthHelper;
import persist.AuthOfy;
import persist.QuizOfy;

import java.util.List;

/**
 * Created by vinaymavi on 19/02/17.
 */
@Api(name = "reveal",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE,},
        title = "Interactive Training",
        description = "Interactive Training Presentation api"

)
public class QuizApi {
    @ApiMethod(name = "quiz.create", path = "quiz_create", httpMethod = "POST")
    public Quiz create(@Named("Token") String token, @Named("Name") String name, @Named("Description") String desc) {
        User user = AuthOfy.getUserByToken(token);
        Quiz quiz = new Quiz(name, desc);
        quiz.setQuizId(AuthHelper.createToken());
        quiz.setUser(user);
        return QuizOfy.loadByKey(QuizOfy.save(quiz));
    }

    @ApiMethod(name = "quiz.list", path = "quiz_list", httpMethod = "POST")
    public List<Quiz> list() {
        return QuizOfy.list();
    }
}
