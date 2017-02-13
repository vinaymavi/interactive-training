package servlet;

import entity.Presentation;
import entity.Session;
import entity.Slide;
import persist.PresentationOfy;
import persist.SessionOfy;
import persist.SlideOfy;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vku131 on 1/23/17.
 */
public class SessionServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(SessionServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("presentation.jsp");
        String sessionId = req.getParameter("session");
        logger.info("PresentationId = " + sessionId);
        Session session = SessionOfy.loadBySessionId(sessionId);
        Presentation presentation = session.getPresentationRef();
        List<Slide> slideList = SlideOfy.listByPresentation(presentation);
        req.setAttribute("presentation", presentation);
        req.setAttribute("slides", slideList);
        try {
            requestDispatcher.forward(req, resp);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
    }
}
