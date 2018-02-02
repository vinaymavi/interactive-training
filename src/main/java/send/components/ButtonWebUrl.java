package send.components;

import java.net.URL;

/**
 * Created by vku131 on 2/2/18.
 */
public class ButtonWebUrl extends Button {
    private URL url;
    public ButtonWebUrl(String title, String type) {
        super(title, type);
    }

    public ButtonWebUrl(String title, URL url) {
        super(title, "web_url");
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
