package persist;

/**
 * Created by vinaymavi on 24/06/15.
 */

import entity.Question;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

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
        logger.warning(Question.class.getName()+" Registered");

    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
