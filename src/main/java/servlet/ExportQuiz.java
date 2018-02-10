package servlet;

import entity.*;
import helper.ExportHelper;
import persist.AnswerOfy;
import persist.QuestionOfy;
import persist.QuizOfy;
import persist.SessionOfy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Server to export quiz result.
 */
public class ExportQuiz extends HttpServlet {
    private static final String CSV_MIME_TYPE = "text/csv";
    private static final String HTML_MIME_TYPE = "text/html";
    private static final Logger logger = Logger.getLogger(ExportQuiz.class.getName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String quizId = req.getParameter("id");
        Set<User> audience;
        List<Question> questions;
        List<Answer> answers;
        ExportHelper exportHelper;
        Quiz quiz = QuizOfy.loadById(quizId);

        try {
            PrintWriter writer = resp.getWriter();
            //Check quiz is exist.
            if (quiz == null) {
                resp.setContentType(HTML_MIME_TYPE);
                writer.write("<h1>Quiz does not exist.</h1>");
                return;
            }

            //CSV creation.
            audience = quiz.getAudience();
            answers = AnswerOfy.loadByQuiz(quiz);
            resp.setContentType(CSV_MIME_TYPE);
            questions = QuestionOfy.questionListByQuiz(quiz);
            exportHelper = new ExportHelper(answers, questions, audience);
            writer.write(exportHelper.createCsv());
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
}
