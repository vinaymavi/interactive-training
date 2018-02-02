package send.components;

/**
 * This is base class of buttons.
 */
public class Button {
    private String title;
    private String type;

    public Button(String title, String type) {
        this.title = title;
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

