package persist;


import com.googlecode.objectify.Key;
import entity.Presentation;
import entity.Question;
import entity.Quiz;
import entity.Slide;

import java.util.List;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vinaymavi on 24/06/15.
 *
 * @description This Objectify class of @code{entity.Question} class.
 */
public class QuestionOfy {
    private static Logger logger = Logger.getLogger(QuestionOfy.class.getName());

    /**
     * This method save Question Entity to datastore.
     *
     * @param question
     * @return
     */
    public static Key<Question> save(Question question) {
        return ofy().save().entity(question).now();
    }

    /**
     * Load Entity from datastore with @code Key
     *
     * @param key
     * @return
     */
    public static Question loadByKey(Key<Question> key) {
        return ofy().load().key(key).safe();
    }

    public static List<Question> listBySlide(Slide slide) {
        logger.warning("Slide Index= " + slide.getIndex());
        return ofy().load().type(Question.class).ancestor(slide).list();
    }

    public static List<Question> listByPresentation(Presentation presentation) {
        logger.warning("Presentation Name = " + presentation.getName());
        return ofy().load().type(Question.class).ancestor(presentation).list();
    }

    public static List<Question> feedbackListByPresentation(Presentation presentation) {
        logger.warning("Presentation Name = " + presentation.getName());
        return ofy().load().type(Question.class).ancestor(presentation).filter("questionNature", "feedback").list();
    }

    public static List<Question> questionListByPresentation(Presentation presentation) {
        logger.warning("Presentation Name = " + presentation.getName());
        return ofy().load().type(Question.class).ancestor(presentation).filter("questionNature", "question").list();
    }

    public static List<Question> questionListByQuiz(Quiz quiz) {
        logger.info("Quiz name=" + quiz.getName());
        List<Question> questionList = ofy().load().type(Question.class).filter("quizRef", quiz).filter("questionNature",
                "quiz").list();
        if (questionList.size() == 0) {
            logger.warning("Empty list");
        }
        return questionList;
    }

    public static Question loadByQuestionId(String questionId) {
        logger.info("QuestionId = " + questionId);
        return ofy().load().type(Question.class).filter("questionId", questionId).first().safe();
    }

}
