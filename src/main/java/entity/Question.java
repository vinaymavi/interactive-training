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
    @Load
    private Ref<Slide> slideRef;
    List<Option> options;
    @Parent
    @Load
    Ref<Presentation> presentationRef;
    @Index
    private String questionNature;
    @Index
    private String questionType;

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
        if (slideRef == null) {
            return null;
        }
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

    public Presentation getPresentationRef() {
        return presentationRef.get();
    }

    public void setPresentationRef(Presentation presentationRef) {
        this.presentationRef = Ref.create(presentationRef);
    }

    public String getQuestionNature() {
        return questionNature;
    }

    /**
     * @param questionNature values could be feedback|question
     */
    public void setQuestionNature(String questionNature) {
        this.questionNature = questionNature;
    }

    public String getQuestionType() {
        return questionType;
    }

    /**
     * @param questionType values cloud be objective:yesno
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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
