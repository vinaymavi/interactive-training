package servlet;

import entity.Answer;
import entity.Question;
import entity.Session;
import entity.User;
import helper.ExportHelper;
import persist.AnswerOfy;
import persist.QuestionOfy;
import persist.SessionOfy;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Servlet to export feedback, session result, quiz result.
 */
public class Export extends HttpServlet {
    private static final String CSV_MIME_TYPE = "text/csv";
    private static final String HTML_MIME_TYPE = "text/html";
    private static final Logger logger = Logger.getLogger(Export.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String sessionId = req.getParameter("id");
        String type = req.getParameter("type");
        Set<User> audience;
        List<Question> questions;
        List<Answer> answers;
        ExportHelper exportHelper;
        Session session = SessionOfy.loadBySessionId(sessionId);

        try {
            PrintWriter writer = resp.getWriter();
            //Check session is exist.
            if (session == null) {
                resp.setContentType(HTML_MIME_TYPE);
                writer.write("<h1>Session not exist.</h1>");
                return;
            }

            //CSV creation.
            audience = session.getAudience();
            answers = AnswerOfy.loadBySession(session);
            resp.setContentType(CSV_MIME_TYPE);
            if (type.equals("feedback")) {
                questions = QuestionOfy.feedbackListByPresentation(session.getPresentationRef());
            } else {
                questions = QuestionOfy.questionListByPresentation(session.getPresentationRef());
            }
            exportHelper = new ExportHelper(answers, questions, audience);
            writer.write(exportHelper.createCsv());
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
}
