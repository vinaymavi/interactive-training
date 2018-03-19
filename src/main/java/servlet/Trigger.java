package servlet;

import com.google.appengine.api.urlfetch.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Answer;
import entity.Quiz;
import entity.User;
import persist.AnswerOfy;
import persist.QuizOfy;
import persist.UserOfy;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Servlet is used to trigger google cloud functions.
 * we are triggering cloud functions with task queue approach.
 */
public class Trigger extends HttpServlet {
    static final String CLOUD_FUNCTION_URL = "https://us-central1-slides-prod.cloudfunctions.net/slidesprodset";
    Logger logger = Logger.getLogger(Trigger.class.getName());
    private URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
    Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("Trigger testing.");
            response.getWriter().write("Trigger");
        } catch (IOException ioException) {
            logger.warning(ioException.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        logger.info("TRIGGER: start");
        String payload = req.getParameter("payload");
        Type stringStringMap = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> payloadMap = gson.fromJson(payload, stringStringMap);
        logger.info("quizId = " + payloadMap.get("quizId") + ", senderId=" + payloadMap.get("senderId"));
        User user = UserOfy.loadBySenderId(payloadMap.get("senderId"));
        Quiz quiz = QuizOfy.loadById(payloadMap.get("quizId"));
        List<Answer> answers = AnswerOfy.loadByQuiz(quiz, user);
        logger.info("Answer list size = " + answers.size());
        Map<String, Object> cloudFunctionPayload = new HashMap<>();
        cloudFunctionPayload.put("user", user);
        cloudFunctionPayload.put("quiz", quiz);
        cloudFunctionPayload.put("answers", answers);
        logger.info("TRIGGER:start cloud function call");
        URL url = null;
        try {
            url = new URL(CLOUD_FUNCTION_URL);
        } catch (MalformedURLException me) {
            logger.warning(me.getMessage());
        }
        HTTPRequest httpRequest = new HTTPRequest(url, HTTPMethod.POST);
        HTTPHeader httpHeader = new HTTPHeader("Content-Type", "application/json");
        httpRequest.setHeader(httpHeader);
        httpRequest.setPayload(gson.toJson(cloudFunctionPayload).getBytes());
        try {
            HTTPResponse httpResponse = fetchService.fetch(httpRequest);
            logger.info(new String(httpResponse.getContent()));
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
        logger.info("TRIGGER:end cloud function call");

    }
}
