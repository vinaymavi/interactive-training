package servlet;

import entity.Session;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Servlet to export feedback, session result, quiz result.
 */
public class Export extends HttpServlet {
    private static final String CSV_MIME_TYPE = "text/csv";
    private static final Logger logger = Logger.getLogger(Export.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String sessionId = req.getParameter("id");
        String type = req.getParameter("type");
        resp.setContentType(CSV_MIME_TYPE);
        try {
            PrintWriter writer = resp.getWriter();
            writer.write("SessionId," + sessionId + "\n");
            writer.write("Type," + type + "\n");
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
}
