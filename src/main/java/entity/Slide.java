package entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.net.URL;

/**
 * Created by vinaymavi on 07/07/15.
 * Entity for slide.
 */
@Entity(name = "slide")
public class Slide {
    @Id
    private Long id;
    @Index
    private String url;
    @Index
    private Integer index;
    @Index
    private String htmlId;
    @Index
    private String session;
    private boolean isPlunk;

    public Slide() {
    }

    public boolean isPlunk() {
        return isPlunk;
    }

    public void setIsPlunk(boolean isPlunk) {
        this.isPlunk = isPlunk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getHtmlId() {
        return htmlId;
    }

    public void setHtmlId(String htmlId) {
        this.htmlId = htmlId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
