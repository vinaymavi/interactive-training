package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.net.URL;
import java.util.Date;

/**
 * Created by vinaymavi on 07/07/15.
 * Entity for slide.
 */
@Entity(name = "slide")
public class Slide {
    @Id
    private Long id;
    private int index;
    @Index
    private String slideId;
    @Index
    private String html;
    private Date addDate;
    private Date updateDate;
    @Parent
    @Load
    Ref<Presentation> presentationRef;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSlideId() {
        return slideId;
    }

    public void setSlideId(String slideId) {
        this.slideId = slideId;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Presentation getPresentationRef() {
        return presentationRef.get();
    }

    public void setPresentationRef(Presentation presentationRef) {
        this.presentationRef = Ref.create(presentationRef);
    }
}
