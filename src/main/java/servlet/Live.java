package servlet;

import com.google.appengine.repackaged.com.google.api.client.http.HttpRequest;
import com.google.appengine.repackaged.com.google.api.client.http.HttpResponse;

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
        String sessionId = request.getParameter("session");
        logger.info("LIVE_RESULT:START - session-id="+sessionId);
        request.setAttribute("name","Git-Essential");
        request.setAttribute("id","sessionid");

        try{
            requestDispatcher.forward(request,response);
        }catch (IOException ioe){
            logger.warning("LIVE_RESULT:ERROR - " +ioe.getMessage());
        }catch (ServletException se){
            logger.warning("LIVE_RESULT:ERROR - " +se.getMessage());
        }

    }
}
