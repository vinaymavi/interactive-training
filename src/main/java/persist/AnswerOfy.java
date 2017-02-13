package persist;

import com.googlecode.objectify.Key;
import entity.Answer;
import entity.Question;

import java.util.*;
import java.util.logging.Logger;

import static persist.OfyService.ofy;

/**
 * Created by vinaymavi on 27/06/15.
 *
 * @description This Objectify class of @code{entity.Answer} class.
 */
public class AnswerOfy {
    public static final Logger logger = Logger.getLogger(AnswerOfy.class.getName());

    /**
     * Save data to datastore.
     *
     * @param answer
     * @return {Key<Answer>}
     */
    public Key<Answer> save(Answer answer) {
        return ofy().save().entity(answer).now();
    }

    /**
     * Load Answer by Key.
     *
     * @param key
     * @return {entity.Answer}
     */
    public Answer loadByKey(Key<Answer> key) {
        return ofy().load().key(key).safe();
    }
}
