package hscsession;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Key;
import entity.Question;
import persist.QuestionOfy;

import javax.inject.Named;
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

        return qf.loadByKey(qf.save(q));
    }
}
