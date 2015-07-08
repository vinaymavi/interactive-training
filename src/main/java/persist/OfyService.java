package persist;

/**
 * Created by vinaymavi on 24/06/15.
 */

import entity.Answer;
import entity.Question;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import entity.Slide;

import java.util.logging.Logger;

/**
 * Give us our Objectify service instead of standard one Also responsible
 * setting up static OfyFactory instead of standard one.
 * *
 */
public class OfyService {
    public static Logger logger = Logger.getLogger(OfyService.class.getName());
    static {
        factory().register(Question.class);
        factory().register(Answer.class);
        factory().register(Slide.class);
        logger.warning("Classes registered for Objectify service.");
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}