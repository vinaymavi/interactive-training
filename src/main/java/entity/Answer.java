package entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vinaymavi on 27/06/15.
 *
 * @Description This is Answer Entity for Google Datastore.
 */
@Entity(name = "Answers")
public class Answer {
    @Id
    private Long id;
    @Index
    private String user;
    @Index
    private String session;
    @Index
    private String groupId;
    private String questionId;
    private int answer;
    private int right_answer;
    @Index
    private boolean giveUp;
    private String result;
    private Date addDate;

    public Answer() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(int right_answer) {
        this.right_answer = right_answer;
    }

    public boolean isGiveUp() {
        return giveUp;
    }

    public void setGiveUp(boolean giveUp) {
        this.giveUp = giveUp;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}
