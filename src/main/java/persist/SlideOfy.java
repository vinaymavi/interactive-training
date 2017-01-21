package persist;

import com.googlecode.objectify.Key;
import entity.Slide;

import java.util.List;

import static persist.OfyService.ofy;

/**
 * Created by vinaymavi on 07/07/15.
 * Objectify class of Slide.
 */
public class SlideOfy {
    /**
     * This method save Question Entity to datastore.
     *
     * @param slide
     * @return
     */
    public static Key<Slide> save(Slide slide) {
        return ofy().save().entity(slide).now();
    }

    /**
     * Load Entity from datastore with @code Key
     *
     * @param key
     * @return
     */
    public static Slide loadByKey(Key<Slide> key) {
        return ofy().load().key(key).safe();
    }

}
