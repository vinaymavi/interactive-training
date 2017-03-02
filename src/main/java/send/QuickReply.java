package send;

import java.net.URL;

/**
 * Created by vku131 on 2/13/17.
 */
public class QuickReply {
    private String content_type;
    private String title;
    private String payload;
    private URL image_url;

    public QuickReply() {

    }

    /**
     * @param content_type {String} text default
     * @param title        {String}
     */
    public QuickReply(String content_type, String title) {
        this.content_type = content_type;
        this.title = title;
    }

    /**
     * @param title {String}
     */
    public QuickReply(String title) {
        this.content_type = "text";
        this.title = title;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public URL getImage_url() {
        return image_url;
    }

    public void setImage_url(URL image_url) {
        this.image_url = image_url;
    }
}
