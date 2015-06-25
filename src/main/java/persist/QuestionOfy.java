package persist;


import com.googlecode.objectify.Key;
import entity.Question;

import static persist.OfyService.ofy;

/**
 * Created by vinaymavi on 24/06/15.
 */
public class QuestionOfy {
    /**
     * This method save Question Entity to datastore.
     *
     * @param question
     * @return
     */
    public Key<Question> save(Question question) {
        return ofy().save().entity(question).now();
    }

    /**
     * Load Entity from datastore with @code Key
     *
     * @param key
     * @return
     */
    public Question loadByKey(Key<Question> key) {
        return ofy().load().key(key).safe();
    }
}
