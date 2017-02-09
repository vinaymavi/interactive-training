package webhook;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class Facebook extends HttpServlet {
    private static Logger logger = Logger.getLogger(Facebook.class.getName());
    private static String VALIDATION_TOKEN = "tT4CLdfi";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.warning("Facebook web hook start");
        String mode = req.getParameter("hub.mode");
        String token = req.getParameter("hub.verify_token");
        String challenge = req.getParameter("hub.challenge");
        logger.warning("Mode = " + mode);
        logger.warning("Token = " + token);
        logger.warning("Challenge = " + challenge);
        try {
            PrintWriter out = resp.getWriter();
            if (VALIDATION_TOKEN.equals(token)) {
                resp.setStatus(200);
                out.write(challenge);
            } else {
                resp.setStatus(403);
                out.write("ERROR");
            }
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        StringBuffer stringBuffer = new StringBuffer("");
        String lineStr;
        try {
            BufferedReader bufferedReader = req.getReader();
            lineStr = bufferedReader.readLine();
            String postReqStr;
            while (lineStr != null) {
                stringBuffer.append(lineStr);
                lineStr = bufferedReader.readLine();
            }
            postReqStr = stringBuffer.toString();
            logger.warning(postReqStr);
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
}
