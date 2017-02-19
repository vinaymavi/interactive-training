package persist;

import com.googlecode.objectify.Key;
import entity.Quiz;

import java.util.List;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vinaymavi on 18/02/17.
 */
public class QuizOfy {
    private static final Logger logger = Logger.getLogger(QuizOfy.class.getName());

    public static Key<Quiz> save(Quiz quiz) {
        logger.warning("Quiz name = " + quiz.getName());
        return ofy().save().entity(quiz).now();
    }

    public static Quiz loadByKey(Key<Quiz> key) {
        logger.warning("key = " + key);
        return ofy().load().key(key).safe();
    }

    public static List<Quiz> list() {
        logger.warning("List all quiz");
        List<Quiz> quizs;
        quizs = ofy().load().type(Quiz.class).list();
        return quizs;
    }
}
