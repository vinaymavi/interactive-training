package persist;

import com.google.appengine.repackaged.com.google.api.services.datastore.DatastoreV1;
import com.googlecode.objectify.Key;
import entity.Presentation;
import entity.User;
import helper.AuthHelper;

import java.util.List;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vku131 on 1/23/17.
 */
public class PresentationOfy {
    private static Logger logger = Logger.getLogger(PresentationOfy.class.getName());
    private static AuthHelper authHelper = new AuthHelper();

    public static Key<Presentation> save(Presentation presentation) {
//        TODO logging missing
        presentation.setPresentationId(authHelper.createToken());
        Key<Presentation> key = ofy().save().entity(presentation).now();
        return key;
    }

    public static Presentation loadByKey(Key<Presentation> key) {
        logger.info("key = " + key);
        return ofy().load().key(key).safe();
    }

    public static Presentation loadById(String presentationId) {
        Presentation presentation = ofy().load().type(Presentation.class).filter("presentationId",presentationId).first().safe();
        return presentation;
    }

    public static List<Presentation> listByUser(User user) {
//        TODO logging missing.
        return ofy().load().type(Presentation.class).ancestor(user).list();
    }
}
