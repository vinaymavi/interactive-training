package helper;

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

    static ListTemplate getQuizList(List<Quiz> quizList, String senderId) {
        List<Element> elementList = new ArrayList<>();
        URL DEFAULT_QUIZ_URL = null;
        try {
            DEFAULT_QUIZ_URL = new URL("https://www.valuecoders.com/blog/wp-content/uploads/2017/02/javascript-frameworks.jpg");
        } catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }
        for (Quiz quiz :
                quizList) {
            Element element = new Element(quiz.getName(), quiz.getDesc(), DEFAULT_QUIZ_URL);
            String subtitle = quiz.getDesc()+System.lineSeparator()+"By - "+quiz.getUser().getFirstName();
            Element element1 = new Element(quiz.getName(),subtitle , DEFAULT_QUIZ_URL);
            Button buttonPostback = new ButtonPostback("Start",ResponsePayloadHelper.startQuizPayLoad(quiz.getQuizId()));
            List<Button> buttonList = new ArrayList<>();
            buttonList.add(buttonPostback);
            element.setButtons(buttonList);
            element1.setButtons(buttonList);
            elementList.add(element);
            elementList.add(element1);
        }
        Payload payload = new Payload("list","compact",null,elementList);
        Attachment attachment = new Attachment("template",payload);
        ListTemplate listTemplate = new ListTemplate(senderId, attachment);
        return listTemplate;
    }
}
