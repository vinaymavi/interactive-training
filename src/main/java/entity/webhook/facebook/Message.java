package entity.webhook.facebook;

import com.googlecode.objectify.annotation.Index;

import java.util.List;
import java.util.Map;

/**
 * Created by vku131 on 2/9/17.
 */
public class Message {
    @Index
    private String mid;
    @Index
    private int seq;
    @Index
    private String text;
    private Map<String, String> quick_reply;
    private String sticker_id;
    private List<Attachment> attachments;

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

    public Map<String, String> getQuick_reply() {
        return quick_reply;
    }

    public void setQuick_reply(Map<String, String> quick_reply) {
        this.quick_reply = quick_reply;
    }

    public String getSticker_id() {
        return sticker_id;
    }

    public void setSticker_id(String sticker_id) {
        this.sticker_id = sticker_id;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
