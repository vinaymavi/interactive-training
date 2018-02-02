package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.net.URL;
import java.util.Date;

/**
 * Created by vinaymavi on 18/02/17.
 */
@Entity(name = "quiz")
public class Quiz {
    @Id
    private Long id;
    @Index
    private String name;
    @Index
    private String desc;
    @Index
    private String quizId;
    private URL shorturl;
    @Load
    @Index
    Ref<User> user;
    private Date addDate;
    private Date updateDate;

    public Quiz() {

    }

    public Quiz(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public User getUser() {
        if (user == null) {
            return null;
        }
        return user.get();
    }

    public void setUser(User user) {
        this.user = Ref.create(user);
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

    public URL getShorturl() {
        return shorturl;
    }

    public void setShorturl(URL shorturl) {
        this.shorturl = shorturl;
    }
}
