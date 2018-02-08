package helper;

import api.Constants;
import constants.Messages;
import entity.Quiz;
import entity.webhook.facebook.FbUserProfile;
import entity.webhook.facebook.MessageEntry;
import entity.webhook.facebook.WebhookPushData;
import send.components.*;
import send.template.GenericTemplate;
import send.template.ListTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vku131 on 2/2/18.
 */
public class GenericTemplateHelper {
    private static Logger logger = Logger.getLogger(GenericTemplate.class.getName());

    public static GenericTemplate createMessage(WebhookPushData webhookPushData, FbUserProfile fbUserProfile, MessageEntry messageEntry) {
        List<Element> elementList = new ArrayList<>();
        URL profileImage = null;
        try {
            profileImage = new URL(fbUserProfile.getProfile_pic());
        } catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }
        String fbUserName = "Name - " + fbUserProfile.getFirst_name() + "" + fbUserProfile.getLast_name();
        Element element = new Element("New User Registraion", fbUserName, profileImage);
        elementList.add(element);
        Button buttonSave = new ButtonPostback("Save", ResponsePayloadHelper.adminMessageAddUser(fbUserProfile, messageEntry));
        Button buttonWrongInfo = new ButtonPostback("Wrong Info", ResponsePayloadHelper.adminMessageAddUser(fbUserProfile, messageEntry));
        List<Button> buttonList = new ArrayList<>();
        buttonList.add(buttonSave);
        buttonList.add(buttonWrongInfo);
        element.setButtons(buttonList);
        Payload payload = new Payload("generic", null, elementList);
        Attachment attachment = new Attachment("template", payload);
        GenericTemplate genericTemplate = new GenericTemplate(Constants.FB_ADMIN_ID, attachment);
        return genericTemplate;
    }

    public static GenericTemplate createMessage(Quiz quiz,String senderId) {
        List<Element> elementList = new ArrayList<>();
        URL DEFAULT_QUIZ_URL = null;
        try {
            DEFAULT_QUIZ_URL = new URL(Constants.DEFAULT_QUIZ_IMAGE);
        } catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }


        StringBuffer subtitleBuffer = new StringBuffer();
        subtitleBuffer.append(quiz.getDesc());
        if (quiz.getUser() != null) {
            subtitleBuffer.append(System.lineSeparator() + "By - " + quiz.getUser().getFirstName());
        }
        Element element = new Element(quiz.getName(), subtitleBuffer.toString(), DEFAULT_QUIZ_URL);
        Button buttonPostback = new ButtonPostback("Start", ResponsePayloadHelper.startQuiz(quiz.getQuizId()));
        List<Button> buttonList = new ArrayList<>();
        buttonList.add(buttonPostback);
        element.setButtons(buttonList);
        elementList.add(element);

        Payload payload = new Payload("generic", null, elementList);
        Attachment attachment = new Attachment("template", payload);
        GenericTemplate genericTemplate = new GenericTemplate(senderId, attachment);
        return genericTemplate;
    }

    public static GenericTemplate unableToHelp(String senderId){
        List<Element> elementList = new ArrayList<>();
        URL imgNotAbleToHelo = null;
        try {
            imgNotAbleToHelo = new URL(Constants.DEFAULT_UNABLETOHELP_IMAGE);
        } catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }
        Element element = new Element(Messages.NOT_ABLE_TO_HELP_TITLE, Messages.NOT_ABLE_TO_HELP_MESSAGE, imgNotAbleToHelo);
        elementList.add(element);

        Payload payload = new Payload("generic", null, elementList);
        Attachment attachment = new Attachment("template", payload);
        GenericTemplate genericTemplate = new GenericTemplate(senderId, attachment);
        return genericTemplate;
    }
}
