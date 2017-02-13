package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.util.Date;

/**
 * Created by vku131 on 1/23/17.
 */
@Entity(name = "auth")
public class Auth {
    @Id
    private Long id;
    @Load
    private Ref<User> userRef;
    @Index
    private String token;
    private Date addDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserRef() {
        return userRef.get();
    }

    public void setUserRef(User userRef) {
        this.userRef = Ref.create(userRef);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}
