package persist;

import com.googlecode.objectify.Key;
import entity.Answer;

import java.util.List;

import static persist.OfyService.ofy;

/**
 * Created by vinaymavi on 27/06/15.
 *
 * @description This Objectify class of @code{entity.Answer} class.
 */
public class AnswerOfy {
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

    /**
     * Load List of Answers by user.
     *
     * @param user
     * @return {List<Answer>}
     */
    public List<Answer> loadByUser(String user) {
        return ofy().load().type(Answer.class).filter("user", user).list();
    }

    /**
     * Load List of Answers by user and questionId.
     *
     * @param questionId
     * @param user
     * @return {List<Answer>}
     */
    public List<Answer> loadByQuesIDAadUser(Long questionId, String user) {
        return ofy().load().type(Answer.class).filter("questionId", questionId).filter("user",user).list();
    }

}
