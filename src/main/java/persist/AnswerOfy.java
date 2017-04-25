package persist;

import com.googlecode.objectify.Key;
import entity.*;

import java.util.*;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * @description This Objectify class of {@link entity.Answer} class.
 */
public class AnswerOfy {
    public static final Logger logger = Logger.getLogger(AnswerOfy.class.getName());

    /**
     * Save data to datastore.
     *
     * @param answer
     * @return {Key<Answer>}
     */
    public static Key<Answer> save(Answer answer) {
        return ofy().save().entity(answer).now();
    }

    /**
     * Load Answer by Key.
     *
     * @param key
     * @return {{@link entity.Answer}}
     */
    public static Answer loadByKey(Key<Answer> key) {
        return ofy().load().key(key).safe();
    }

    /**
     * Load all answers of a quiz.
     *
     * @param quiz {{@link Quiz}}
     * @return {{{@link List<Quiz>}}}
     */
    public static List<Answer> loadByQuiz(Quiz quiz) {
        logger.info("Quiz Name = " + quiz.getName());
        return ofy().load().type(Answer.class).filter("quizRef", quiz).list();
    }

    /**
     * @param quiz {{@link Quiz}}
     * @param user {{@link User}}
     * @return {{@link List<Answer>}}
     */
    public static List<Answer> loadByQuiz(Quiz quiz, User user) {
        logger.info("Quiz Name = " + quiz.getName() + ", User name = " + user.getFirstName());
        return ofy().load().type(Answer.class).filter("quizRef", quiz).filter("userRef", user).list();
    }

    /**
     * @param session {{@link Session}}
     * @return {{@link List<Answer>}}
     */
    public static List<Answer> loadBySession(Session session) {
        logger.info("Session Name = " + session.getName());
        return ofy().load().type(Answer.class).filter("sessionRef", session).list();
    }
}
