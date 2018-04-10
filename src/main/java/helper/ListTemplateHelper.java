package helper;

import api.Constants;
import entity.Quiz;

import send.components.*;
import send.template.ListTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/2/18.
 */
public class ListTemplateHelper {
    private static Logger logger = Logger.getLogger(ListTemplateHelper.class.getName());

    static ListTemplate createMessage(List<Quiz> quizList, String senderId) {
        List<Element> elementList = new ArrayList<>();
        URL DEFAULT_QUIZ_URL = null;
        try {
            DEFAULT_QUIZ_URL = new URL(Constants.DEFAULT_QUIZ_IMAGE);
        } catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }
        for (Quiz quiz :
                quizList) {
            
            StringBuffer subtitleBuffer = new StringBuffer();
            subtitleBuffer.append(quiz.getDesc());
            if(quiz.getUser() != null){
                subtitleBuffer.append(System.lineSeparator()+"By - "+quiz.getUser().getFirstName());
            }            
            Element element = new Element(quiz.getName(), subtitleBuffer.toString(), DEFAULT_QUIZ_URL);
            Button buttonPostback = new ButtonPostback("Start",ResponsePayloadHelper.startQuiz(quiz.getQuizId()));
            List<Button> buttonList = new ArrayList<>();
            buttonList.add(buttonPostback);
            element.setButtons(buttonList);            
            elementList.add(element);            
        }
        Payload payload = new ListTemplatePayload("list","compact",null,elementList);
        Attachment attachment = new Attachment("template",payload);
        ListTemplate listTemplate = new ListTemplate(senderId, attachment);
        return listTemplate;
    }
}
