package entity.webhook.facebook;

/**
 * Created by vku131 on 2/9/17.
 */
public class Message {
    private String mid;
    private int seq;
    private String text;

    public Message() {
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
