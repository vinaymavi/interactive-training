package api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import entity.*;
import helper.AuthHelper;
import helper.QuestionHelper;
import persist.PresentationOfy;
import persist.QuestionOfy;
import persist.QuizOfy;
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
    public Question create(@Named("token") String token, @Nullable @Named("slide") String slideId, @Nullable @Named
            ("presentationId") String pId, @Nullable @Named("quizId") String quizId, Question question) {

        question.setQuestionId(AuthHelper.createToken());
        Slide slide;
        Presentation presentation;
        Quiz quiz;
        if (slideId == null && pId == null && quizId == null) {
            logger.severe("SlideId,PresentationId,QuizId at-least one required");
            return null;
        }
        // slide id to register question on a perticular slide.
        if (slideId != null && slideId instanceof String) {
            slide = SlideOfy.loadBySlideId(slideId);
            question.setSlideRef(slide);
        } else if (pId != null && pId instanceof String) {
            presentation = PresentationOfy.loadByPresentationId(pId);
            question.setPresentationRef(presentation);
        } else if (quizId != null && quizId instanceof String) {
            quiz = QuizOfy.loadById(quizId);
            question.setQuizRef(quiz);
        }

        if (question.getQuestionNature() != null && question.getQuestionNature().equals("feedback")) {
            question.setOptions(QuestionHelper.createFeedbackOptions(question.getQuestionType()));
        } else {
            Option rightOption = QuestionHelper.getRightOptions(question.getOptions());
            question.setRightOption(rightOption);
        }

        return QuestionOfy.loadByKey(QuestionOfy.save(question));
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

    @ApiMethod(name = "question.list.quiz", path = "question_list_quiz")
    public List<Question> questionQuizList(@Named("token") String token, @Named("quizId") String quizId) {
        logger.info("quizId =" + quizId);
        Quiz quiz = QuizOfy.loadById(quizId);
        return QuestionOfy.questionListByQuiz(quiz);
    }

}
