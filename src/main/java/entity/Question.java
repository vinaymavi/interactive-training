package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

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
    @Index
    private String questionId;
    @Index
    private String desc;
    @Parent
    @Load
    private Ref<Slide> slideRef;
    List<Option> options;

    Option rightOption;
    private Date addDate;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Slide getSlideRef() {
        return slideRef.get();
    }

    public void setSlideRef(Slide slideRef) {
        this.slideRef = Ref.create(slideRef);
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Option getRightOption() {
        return rightOption;
    }

    public void setRightOption(Option rightOption) {
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
