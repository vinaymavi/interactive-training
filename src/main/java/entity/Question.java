package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

import java.util.Date;
import java.util.List;

/**
 * Created by vinaymavi on 24/06/15.
 *
 * @description This is Question Entity for Google Datastore.
 */
@Entity(name = "question")
public class Question {
    @Id
    private Long id;
    @Parent
    @Load
    private Ref<Slide> slideRef;
    List<Option> options;
    @Load
    Ref<Option> rightOption;
    private Date addDate;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ref<Slide> getSlideRef() {
        return slideRef;
    }

    public void setSlideRef(Ref<Slide> slideRef) {
        this.slideRef = slideRef;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Ref<Option> getRightOption() {
        return rightOption;
    }

    public void setRightOption(Ref<Option> rightOption) {
        this.rightOption = rightOption;
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
}
