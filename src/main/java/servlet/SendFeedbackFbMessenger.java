package servlet;

import com.googlecode.objectify.annotation.Load;
import entity.Presentation;
import entity.Question;
import entity.Session;
import entity.User;
import helper.QuestionHelper;
import persist.PresentationOfy;
import persist.QuestionOfy;
import persist.SessionOfy;
import send.Facebook;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/14/17.
 */
public class SendFeedbackFbMessenger extends HttpServlet {
    private static Logger logger = Logger.getLogger(SendFeedbackFbMessenger.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.warning("Bulk Messaging start");
        String sessionId = req.getParameter("sid");
        Session session = SessionOfy.loadBySessionId(sessionId);
        Presentation presentation = session.getPresentationRef();
        List<Question> questionList = QuestionOfy.feedbackListByPresentation(presentation);
        logger.warning("question list size = " + questionList.size());
        if (questionList.size() > 0) {
            Question question = questionList.get(0);
            Facebook facebook = new Facebook();
            logger.warning("Session name = " + session.getName());
            List<User> userList = session.getAudience();
            logger.warning("Audience list size = " + userList.size());
            for (User user : userList) {
                facebook.sendMessage(QuestionHelper.createFbTextMsg(user, question));
            }
        }
        logger.warning("Bulk Messaging End");
    }
}
