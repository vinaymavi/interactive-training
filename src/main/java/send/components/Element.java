package send.components;

import java.net.URL;
import java.util.List;

/**
 * Created by vku131 on 2/2/18.
 */
public class Element {
    private String title;
    private String subtitle;
    private URL image_url;
    private List<Button> buttons;

    public Element(String title, String subTitle, URL image_url) {
        this.title = title;
        this.subtitle = subTitle;
        this.image_url = image_url;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }
}
