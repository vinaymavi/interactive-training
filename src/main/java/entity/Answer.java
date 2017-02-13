package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.util.Date;

/**
 * Created by vinaymavi on 27/06/15.
 *
 * @Description This is Answer Entity for Google Datastore.
 */
@Entity(name = "answer")
public class Answer {
    @Id
    private Long id;
    @Parent
    @Load
    private Ref<Question> questionRef;
    @Load
    private Ref<User> userRef;
    @Load
    private Ref<Session> sessionRef;
    private Boolean isCorrect;
    private Date addDate;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ref<Question> getQuestionRef() {
        return questionRef;
    }

    public void setQuestionRef(Ref<Question> questionRef) {
        this.questionRef = questionRef;
    }

    public Ref<User> getUserRef() {
        return userRef;
    }

    public void setUserRef(Ref<User> userRef) {
        this.userRef = userRef;
    }

    public Ref<Session> getSessionRef() {
        return sessionRef;
    }

    public void setSessionRef(Ref<Session> sessionRef) {
        this.sessionRef = sessionRef;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
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
