package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import entity.Option;
import entity.Question;
import entity.Slide;
import helper.AuthHelper;
import helper.QuestionHelper;
import persist.QuestionOfy;
import persist.SlideOfy;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vku131 on 1/22/17.
 */
@Api(name = "reveal",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE,},
        title = "Interactive Training",
        description = "Interactive Training Presentation api"

)
public class QuestionApi {
    private static Logger logger = Logger.getLogger(QuestionApi.class.getName());

    @ApiMethod(name = "question.create", path = "question_create")
    public Question create(@Named("token") String token, @Named("slide") String slideId, Question question) {
        question.setQuestionId(AuthHelper.createToken());
        Slide slide = SlideOfy.loadBySlideId(slideId);
        question.setSlideRef(slide);
        Option rightOption = QuestionHelper.getRightOptions(question.getOptions());
        question.setRightOption(rightOption);
        return QuestionOfy.loadByKey(QuestionOfy.save(question));
    }

    @ApiMethod(name = "question.list", path = "question_list")
    public List<Question> questionList(@Named("token") String token, @Named("slideId") String slideId) {
        Slide slide = SlideOfy.loadBySlideId(slideId);
        return QuestionOfy.listBySlide(slide);
    }
}
