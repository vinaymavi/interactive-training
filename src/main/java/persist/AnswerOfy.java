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
        return ofy().load().type(Answer.class).filter("questionId", questionId).filter("user", user).list();
    }

    /**
     * Load answers by session name.
     *
     * @param session
     * @return List<Answer>
     */
    public static List<Answer> loadRightResult(String session) {
        return ofy().load().type(Answer.class).filter("session", session).filter("result", "RIGHT").list();
    }

    /**
     * Load answers by session name.
     *
     * @param session
     * @return List<Answer>
     */
    public static List<Answer> loadRightResult(String session, String user) {
        return ofy().load().type(Answer.class).filter("session", session).filter("user", user).filter("result", "RIGHT").list();
    }

    /**
     * returns Users set.
     *
     * @param session
     * @return
     */
    public static Set<String> sessionUsers(String session) {
        List<Answer> answerList = AnswerOfy.loadRightResult(session);
        Iterator<Answer> itr = answerList.iterator();
        Set<String> users = new HashSet<String>();
        while (itr.hasNext()) {
            users.add(itr.next().getUser());
        }
        return users;
    }

    /**
     * Return session result percentage.
     *
     * @return
     */
    public static Map<String, Float> sessionResult(String session) {
        logger.warning(session);
        List<Answer> answerList = AnswerOfy.loadRightResult(session);
        List<Question> questionList = QuestionOfy.loadBySession(session);
        Set<String> users = AnswerOfy.sessionUsers(session);

        logger.warning("Number of users=" + users.size());
        logger.warning("Number of answers=" + answerList.size());
        logger.warning("Number of questions=" + questionList.size());
        Map<String, Float> map = new HashMap<String, Float>();
        Float result = ((float) answerList.size() * 100) / (questionList.size() * users.size());
        map.put("result", result);
        return map;
    }

    public static Map<String, Float> userResult(String session, String user) {
        List<Answer> answerList = AnswerOfy.loadRightResult(session, user);
        List<Question> questionList = QuestionOfy.loadBySession(session);
        logger.warning("Number of answers=" + answerList.size());
        logger.warning("Number of questions=" + questionList.size());
        Map<String, Float> map = new HashMap<String, Float>();
        Float result = ((float) answerList.size() * 100) / questionList.size();
        map.put("result", result);
        return map;
    }

}
