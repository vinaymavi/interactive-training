package persist;

import com.googlecode.objectify.Key;
import entity.Presentation;
import entity.Slide;

import java.util.List;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vinaymavi on 07/07/15.
 * Objectify class of Slide.
 */
public class SlideOfy {
    private static Logger logger = Logger.getLogger(SlideOfy.class.getName());

    /**
     * This method save Question Entity to datastore.
     *
     * @param slide
     * @return
     */
    public static Key<Slide> save(Slide slide) {
//    TODO    Logging missing.
        return ofy().save().entity(slide).now();
    }

    /**
     * Load Entity from datastore with @code Key
     *
     * @param key
     * @return
     */
    public static Slide loadByKey(Key<Slide> key) {
        logger.info("Key = " + key);
        return ofy().load().key(key).safe();
    }

    public static List<Slide> listByPresentation(Presentation presentation) {
//    TODO logging missing
        return ofy().load().type(Slide.class).ancestor(presentation).list();
    }

}
