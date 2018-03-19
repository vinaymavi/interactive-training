package api;

import com.google.api.server.spi.config.ApiMethod;
import entity.Answer;
import entity.Quiz;
import entity.User;
import persist.AnswerOfy;
import persist.QuizOfy;
import persist.UserOfy;

import javax.inject.Named;
import java.util.List;

/**
 * Return answers by sessionid and userid
 */
public class AnswerApi extends ApiBaseV1 {
    @ApiMethod(name = "answer.userAnswers",path = "answer_user_answer",httpMethod = "POST")
    public List<Answer> getUserAnswer(@Named("SenderId") String senderid,@Named("quizId") String quizId){
        User user = UserOfy.loadBySenderId(senderid);
        Quiz quiz = QuizOfy.loadById(quizId);
        List<Answer> answers = AnswerOfy.loadByQuiz(quiz,user);
        return answers;
    }
}
