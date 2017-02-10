package entity.webhook.facebook;

import com.googlecode.objectify.annotation.Index;

import java.util.Map;

/**
 * Created by vku131 on 2/9/17.
 */
public class Attachment {
    @Index
    private String type;
    private Map<String, Object> payload;
    @Index
    private String title;
    @Index
    private String url;

    public Attachment() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
