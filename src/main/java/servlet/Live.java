package servlet;

import com.google.appengine.repackaged.com.google.api.client.http.HttpRequest;
import com.google.appengine.repackaged.com.google.api.client.http.HttpResponse;
import entity.Quiz;
import persist.QuizOfy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * This servlet is used to display live quiz result to audience,need quiz id as parameter.
 */
public class Live extends HttpServlet {
    Logger logger = Logger.getLogger(Live.class.getName());
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("live.jsp");
        String quizId = request.getParameter("quiz");
        logger.info("LIVE_RESULT:START - session-id="+quizId);
        Quiz quiz = QuizOfy.loadById(quizId);
        request.setAttribute("name",quiz.getName());
        request.setAttribute("id",quizId);

        try{
            requestDispatcher.forward(request,response);
        }catch (IOException ioe){
            logger.warning("LIVE_RESULT:ERROR - " +ioe.getMessage());
        }catch (ServletException se){
            logger.warning("LIVE_RESULT:ERROR - " +se.getMessage());
        }

    }
}
