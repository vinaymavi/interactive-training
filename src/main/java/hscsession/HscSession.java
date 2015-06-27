package hscsession;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Key;
import entity.Answer;
import entity.Question;
import persist.AnswerOfy;
import persist.QuestionOfy;

import javax.inject.Named;
import java.util.Date;
import java.util.List;
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

    @ApiMethod(name = "addQuestion")
    public Question addQuestion(@Named("session") String session,
                                @Named("groupId") String groupId,
                                @Named("question") String question,
                                @Named("option1") String option1,
                                @Named("option2") String option2,
                                @Named("option3") String option3,
                                @Named("option4") String option4,
                                @Named("rightOption") int rightOption
    ) {
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

        return qf.loadByKey(qf.save(q));
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
                            @Named("questionId") String questionId,
                            @Named("answer") int answer,
                            @Named("rightAnswer") int rightAnswer,
                            @Named("result") String result,
                            @Named("giveUp") boolean giveUp) {
        AnswerOfy af = new AnswerOfy();
        Answer a = new Answer();
        a.setUser(user);
        a.setSession(session);
        a.setGroupId(groupId);
        a.setQuestionId(questionId);
        a.setAnswer(answer);
        a.setRight_answer(rightAnswer);
        a.setResult(result);
        a.setAddDate(new Date());
        a.setGiveUp(giveUp);
        return af.loadByKey(af.save(a));
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
}
