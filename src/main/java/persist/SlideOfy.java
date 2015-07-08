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

    /**
     * This is function query the datastore and return list of Slide class.
     *
     * @param htmlId
     * @return List<Slide>
     */
    public static List<Slide> loadByHtmlId(String htmlId) {
        return ofy().load().type(Slide.class).filter("htmlId", htmlId).list();
    }

    /**
     * List of slides by Index.
     *
     * @param index
     * @return List<Slide>
     */
    public static List<Slide> loadByIndex(String index) {
        return ofy().load().type(Slide.class).filter("index", index).list();
    }

    /**
     * List of slides by Index.
     *
     * @param session
     * @return List<Slide>
     */
    public static List<Slide> loadBySession(String session) {
        return ofy().load().type(Slide.class).filter("session", session).order("index").list();
    }
}
