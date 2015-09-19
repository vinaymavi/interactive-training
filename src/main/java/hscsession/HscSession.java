package hscsession;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Nullable;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import entity.Answer;
import entity.Question;
import entity.Slide;
import persist.AnswerOfy;
import persist.QuestionOfy;
import persist.SlideOfy;

import javax.inject.Named;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Add your first API methods in this class, or you may create another class. In that case, please
 * update your web.xml accordingly.
 */

@Api(name = "hscsession",
        version = "v1",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE}
)

public class HscSession {
    private final static Logger logger = Logger.getLogger(HscSession.class.getName());
    private static final String EMAILS = "vinaymavi@hotmail.com,vinaymavi@gmail.com,viney.yadav@gmail.com";

    /**
     * @param session
     * @param groupId
     * @param question
     * @param option1
     * @param option2
     * @param option3
     * @param option4
     * @param rightOption
     * @param level       level should be B/I/A
     * @param type        type should P/T
     * @param user
     * @return
     */
    @ApiMethod(name = "addQuestion")
    public Question addQuestion(@Named("session") String session,
                                @Named("groupId") String groupId,
                                @Named("question") String question,
                                @Named("option1") String option1,
                                @Named("option2") String option2,
                                @Named("option3") String option3,
                                @Named("option4") String option4,
                                @Named("level") String level,
                                @Named("rightOption") int rightOption,
                                @Named("type") String type,
                                User user) {
        if (user != null && EMAILS.contains(user.getEmail())) {
            Question q = new Question();
            QuestionOfy qf = new QuestionOfy();

            q.setSession(session);
            q.setGroupId(groupId);
            q.setQuestion(question);
            q.setOption1(option1);
            q.setOption2(option2);
            q.setOption3(option3);
            q.setOption4(option4);
            q.setRightOption(rightOption);
            q.setAddDate(new Date());
            q.setLevel(level);
            q.setType(type);

            return qf.loadByKey(qf.save(q));
        }

        return null;
    }

    /**
     * Return List of Questions by groupid.
     *
     * @param groupId
     * @return {List<Question>}
     */
    @ApiMethod(name = "questionsByGroupId")
    public List<Question> questionsByGroupId(@Named("groupid") String groupId) {
        QuestionOfy qf = new QuestionOfy();
        return qf.loadByGroupId(groupId);
    }

    @ApiMethod(name = "addAnswer")
    public Answer addAnswer(@Named("user") String user,
                            @Named("session") String session,
                            @Named("groupId") String groupId,
                            @Named("questionId") Long questionId,
                            @Named("answer") int answer,
                            @Named("rightAnswer") int rightAnswer,
                            @Named("result") String result,
                            @Named("level") String level,
                            @Named("giveUp") boolean giveUp,
                            @Named("type") String type) {
        AnswerOfy af = new AnswerOfy();
        Answer a;

        List<Answer> answers = af.loadByQuesIDAadUser(questionId, user);
        if (answers.size() > 0) {
            a = answers.get(0);
            a.setAnswer(answer);
            a.setResult(result);
            return af.loadByKey(af.save(a));
        } else {
            a = new Answer();
            a.setUser(user);
            a.setSession(session);
            a.setGroupId(groupId);
            a.setQuestionId(questionId);
            a.setAnswer(answer);
            a.setRight_answer(rightAnswer);
            a.setResult(result);
            a.setAddDate(new Date());
            a.setGiveUp(giveUp);
            a.setLevel(level);
            a.setType(type);
            return af.loadByKey(af.save(a));
        }
    }

    /**
     * Return list of answers by user.
     *
     * @param user
     * @return {List<Answer>}
     */
    @ApiMethod(name = "answerByUser")
    public List<Answer> answersByUser(@Named("user") String user) {
        AnswerOfy af = new AnswerOfy();
        return af.loadByUser(user);
    }

    @ApiMethod(name = "quesionByQuesIdAndUser")
    public List<Answer> quesionByQuesIdAndUser(@Named("questionId") Long questionId, @Named("user") String user) {
        AnswerOfy af = new AnswerOfy();
        return af.loadByQuesIDAadUser(questionId, user);
    }

    @ApiMethod(name = "slides", httpMethod = "GET")
    public List<Slide> slides(@Named("sessionName") String session) {
        return SlideOfy.loadBySession(session);
    }

    @ApiMethod(name = "addSlide")
    public Slide addSlide(@Named("session") String session,
                          @Named("index") Integer index,
                          @Named("htmlId") String htmlId,
                          @Named("isPlunk") @Nullable Boolean isPlunk,
                          @Named("url") String url, User user) throws Exception {
        if (user != null && EMAILS.contains(user.getEmail())) {
            Slide slide = new Slide();
            slide.setUrl(url.trim());
            slide.setSession(session);
            slide.setHtmlId(htmlId);
            slide.setIndex(index);
            slide.setIsPlunk(isPlunk);
            return SlideOfy.loadByKey(SlideOfy.save(slide));
        }
        return null;
    }

    @ApiMethod(name = "sessionResult")
    public Map<String, Float> sessionResult(@Named("session") String session) {
        return AnswerOfy.sessionResult(session);
    }

    @ApiMethod(name = "userResult")
    public Map<String, Float> userResult(@Named("session") String session, @Named("user") String user) {
        return AnswerOfy.userResult(session, user);
    }
}
