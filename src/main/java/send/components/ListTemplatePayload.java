package send.components;

import java.util.List;

/**
 * Created by vku131 on 2/2/18.
 */
public class ListTemplatePayload extends Payload {
    private String top_element_style;

    public ListTemplatePayload(String template_type, String top_element_style, List<Button> buttons, List<Element> elements) {
        super(template_type, buttons, elements);
        this.top_element_style = top_element_style;
    }
}
