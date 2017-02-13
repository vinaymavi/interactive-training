package servlet;

import entity.Presentation;
import entity.Slide;
import persist.PresentationOfy;
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
public class PresentationServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(PresentationServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("presentation.jsp");
        String presentationId = req.getParameter("presentation");
        logger.info("PresentationId = " + presentationId);
        Presentation presentation = PresentationOfy.loadByPresentationId(presentationId);
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
