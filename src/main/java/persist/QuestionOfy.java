package persist;


import com.googlecode.objectify.Key;
import entity.Question;

import java.util.List;

import static persist.OfyService.ofy;

/**
 * Created by vinaymavi on 24/06/15.
 *
 * @description This Objectify class of @code{entity.Question} class.
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

    /**
     * This is function query the datastore and return list of Question class.
     *
     * @param groupId
     * @return List<Question>
     */
//    TODO session also required for query.
    public List<Question> loadByGroupId(String groupId) {
        return ofy().load().type(Question.class).filter("groupId", groupId).list();
    }

    /**
     * Get Question list by session.
     *
     * @param session
     * @return List<Question>
     */
    public static List<Question> loadBySession(String session) {
        return ofy().load().type(Question.class).filter("session", session).list();
    }

}
