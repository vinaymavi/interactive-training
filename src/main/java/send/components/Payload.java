package send.components;

import java.util.List;

/**
 * Created by vku131 on 2/2/18.
 */
public class Payload {
    private String template_type;
    private List<Button> buttons;
    private List<Element> elements;

    public Payload(String template_type, List<Button> buttons, List<Element> elements) {
        this.template_type = template_type;
        this.buttons = buttons;
        this.elements = elements;
    }

}
