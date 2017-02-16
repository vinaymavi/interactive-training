package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import entity.Option;
import entity.Presentation;
import entity.Question;
import entity.Slide;
import helper.AuthHelper;
import helper.QuestionHelper;
import persist.PresentationOfy;
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
    public Question create(@Named("token") String token, @Nullable @Named("slide") String slideId, @Named("presentationId") String pId, Question question) {
        question.setQuestionId(AuthHelper.createToken());
        if (slideId != null & slideId instanceof String) {
            Slide slide = SlideOfy.loadBySlideId(slideId);
            question.setSlideRef(slide);
        }
        if (question.getQuestionNature() != null & question.getQuestionNature().equals("question")) {
            Option rightOption = QuestionHelper.getRightOptions(question.getOptions());
            question.setRightOption(rightOption);
        } else {
            question.setOptions(QuestionHelper.createFeedbackOptions(question.getQuestionType()));
        }
        Presentation presentation = PresentationOfy.loadByPresentationId(pId);
        if (presentation != null) {
            question.setPresentationRef(presentation);
            return QuestionOfy.loadByKey(QuestionOfy.save(question));
        }
        return null;
    }

    @ApiMethod(name = "question.list.slide", path = "question_list_slide")
    public List<Question> questionList(@Named("token") String token, @Named("slideId") String slideId) {
        Slide slide = SlideOfy.loadBySlideId(slideId);
        return QuestionOfy.listBySlide(slide);
    }

    @ApiMethod(name = "question.list.presentation", path = "question_list_presentation")
    public List<Question> questionPresentationList(@Named("token") String token, @Named("presentationId") String pId) {
        logger.warning("PresentationId =" + pId);
        Presentation presentation = PresentationOfy.loadByPresentationId(pId);
        return QuestionOfy.listByPresentation(presentation);
    }
}
