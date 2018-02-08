package send;

import com.google.appengine.api.urlfetch.*;
import com.google.gson.Gson;
import entity.config.Config;
import persist.ConfigOfy;

import javax.xml.soap.Text;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * File use to send all message to facebook platform.
 */
public class Facebook {
    private Logger logger = Logger.getLogger(webhook.Facebook.class.getName());
    private String GROUP_NAME = "facebook";
    private String BASE_URL_CONFIG = "base_url";
    private String PAGE_ACCESS_TOKEN = "page_access_token";
    private String baseUrl;
    private String pageAccessToken;
    private URL url;
    private List<Config> configList;
    private Config config;
    private Map<String, Object> configMap;
    private URLFetchService fetchService = URLFetchServiceFactory.getURLFetchService();
    private HTTPRequest httpRequest;
    private HTTPResponse httpResponse;
    private HTTPHeader httpHeader = new HTTPHeader("Content-Type", "application/json");
    private Gson gson = new Gson();

    public Facebook() {
        this.configList = ConfigOfy.loadByGroupName(GROUP_NAME);
        if (configList != null) {
            this.config = configList.get(0);
            this.configMap = config.getConfigMap();
            this.baseUrl = (String) configMap.get(BASE_URL_CONFIG);
            this.pageAccessToken = (String) configMap.get(PAGE_ACCESS_TOKEN);
            try {
                this.url = new URL(baseUrl + pageAccessToken);
            } catch (MalformedURLException mfe) {
                logger.warning(mfe.getMessage());
            }

        } else {
            logger.warning("No config found for give group = " + GROUP_NAME);
        }
    }

    public Map<String, String> sendMessage(String msgPayload) {
        logger.info("msgPayload = " + msgPayload);
        this.httpRequest = new HTTPRequest(this.url, HTTPMethod.POST);
        this.httpRequest.setHeader(httpHeader);
        this.httpRequest.setPayload(msgPayload.getBytes());
        String respStr;
        Map<String, String> respMap = new HashMap<>();
        try {
            httpResponse = fetchService.fetch(httpRequest);
            respStr = new String(httpResponse.getContent());
            respMap.put("resp", respStr);
            logger.warning("httpRespFB = " + respStr);
            return respMap;
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
        return respMap;
    }

    public List<Map<String, String>> sendMessage(List<TextMessage> list) {
        List<Map<String, String>> respList = new ArrayList<>();
        for (TextMessage textMessage : list) {
            respList.add(this.sendMessage(gson.toJson(textMessage)));
        }
        return respList;
    }

    public Map<String, String> sendMessage(TextMessage textMessage) {
        return this.sendMessage(gson.toJson(textMessage));
    }

    public String getUserProfile(String senderId) {
        logger.warning("SenderId = " + senderId);
        URL url;
        String respStr = null;
        try {
            url = new URL("https://graph.facebook.com/v2.8/" + senderId + "?access_token=" + this.pageAccessToken);
            logger.warning("Profile URl = " + url);
            HTTPRequest httpRequest = new HTTPRequest(url, HTTPMethod.GET);
            HTTPResponse httpResponse = fetchService.fetch(httpRequest);
            respStr = new String(httpResponse.getContent());
            logger.warning("httpRespFB = " + respStr);
            return respStr;
        } catch (MalformedURLException mue) {
            logger.warning(mue.getMessage());
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
        return respStr;
    }
}
