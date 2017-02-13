package persist;

/**
 * Created by vinaymavi on 24/06/15.
 */

import entity.*;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import entity.config.Config;
import entity.webhook.facebook.WebhookPushData;

import java.util.logging.Logger;

/**
 * Give us our Objectify service instead of standard one Also responsible
 * setting up static OfyFactory instead of standard one.
 * *
 */
public class OfyService {
    public static Logger logger = Logger.getLogger(OfyService.class.getName());
    static {
        factory().register(Answer.class);
        factory().register(Presentation.class);
        factory().register(Question.class);
        factory().register(Session.class);
        factory().register(Slide.class);
        factory().register(User.class);
        factory().register(Auth.class);
        factory().register(WebhookPushData.class);
        factory().register(Config.class);
        logger.warning("Classes registered for Objectify service.");
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
