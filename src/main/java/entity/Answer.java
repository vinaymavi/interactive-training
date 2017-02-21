package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;
import helper.AuthHelper;

import java.util.Date;

/**
 * @Desc This answer entity will store all type of answers.
 * question,feedback
 */
@Entity(name = "answer")
public class Answer {
    @Id
    private Long id;
    @Index
    private String answerId;
    @Parent
    @Load
    private Ref<Question> questionRef;
    @Index
    @Load
    private Ref<User> userRef;
    @Index
    @Load
    private Ref<Session> sessionRef;
    @Index
    @Load
    private Ref<Quiz> quizRef;
    @Index
    private Boolean isRight;
    private String questionNature;
    private Date addDate;
    private Date updateDate;

    public Answer() {

    }

    /**
     * @param user     {{@Link User}}
     * @param question {{{@link Question}}}
     * @param isRight  {{@link Boolean}}
     */
    public Answer(User user, Question question, boolean isRight) {
        this.userRef = Ref.create(user);
        this.isRight = isRight;
        this.questionNature = question.getQuestionNature();
        this.answerId = AuthHelper.createToken();
        this.questionRef = Ref.create(question);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public Question getQuestionRef() {
        if (questionRef == null) {
            return null;
        }
        return questionRef.get();
    }

    public void setQuestionRef(Question questionRef) {
        this.questionRef = Ref.create(questionRef);
    }

    public User getUserRef() {
        if (userRef == null) {
            return null;
        }
        return userRef.get();
    }

    public void setUserRef(User userRef) {
        this.userRef = Ref.create(userRef);
    }

    public Session getSessionRef() {
        if (sessionRef == null) {
            return null;
        }
        return sessionRef.get();
    }

    public void setSessionRef(Session sessionRef) {
        this.sessionRef = Ref.create(sessionRef);
    }

    public Quiz getQuizRef() {
        if (quizRef == null) {
            return null;
        }
        return quizRef.get();
    }

    public void setQuizRef(Quiz quizRef) {
        this.quizRef = Ref.create(quizRef);
    }

    public String getQuestionNature() {
        return questionNature;
    }

    public void setQuestionNature(String questionNature) {
        this.questionNature = questionNature;
    }

    public Boolean isRight() {
        return isRight;
    }

    public void setRight(Boolean correct) {
        isRight = correct;
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
