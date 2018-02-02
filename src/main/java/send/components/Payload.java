package send.components;

import java.util.List;

/**
 * Created by vku131 on 2/2/18.
 */
public class Payload {
    private String template_type;
    private String top_element_style;
    private List<Button> buttons;
    private List<Element> elements;

    public Payload(String template_type, String top_element_style, List<Button> buttons, List<Element> elements) {
        this.template_type = template_type;
        this.top_element_style = top_element_style;
        this.buttons = buttons;
        this.elements = elements;
    }

}
