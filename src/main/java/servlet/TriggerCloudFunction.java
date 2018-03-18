package servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Servlet is used to trigger google cloud functions.
 * we are triggering cloud functions with task queue approach.
 */
public class TriggerCloudFunction extends HttpServlet {
     static final String CLOUD_FUNCTION_URL = "";
     Logger logger = Logger.getLogger(TriggerCloudFunction.class.getName());
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        String quizId = request.getParameter("quiz");
        try {
            logger.info("called.");
            response.getWriter().write("Done.");
        }catch (IOException ioException){
            logger.warning(ioException.getMessage());
        }
    }
}
