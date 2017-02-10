package webhook;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import entity.webhook.facebook.WebhookPushData;
import persist.WebhookPushDataOfy;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Facebook extends HttpServlet {
    private static Logger logger = Logger.getLogger(Facebook.class.getName());
    private static String VALIDATION_TOKEN = "tT4CLdfi";
    private static Gson gson = new Gson();

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
        Map<String,String> respMap = new HashMap<>();
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
            WebhookPushData webhookPushData = gson.fromJson(postReqStr, WebhookPushData.class);
            Key<WebhookPushData> key = WebhookPushDataOfy.save(webhookPushData);
            logger.warning("key = "+key);
            respMap.put("status","OK");
            respMap.put("key",key.toString());
            resp.getWriter().write(gson.toJson(respMap));
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
}
