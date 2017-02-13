package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by vku131 on 1/20/17.
 */
@Entity(name = "presentation")
public class Presentation {
    @Id
    private Long id;
    @Index
    private String presentationId;
    @Parent
    @Load
    private Ref<User> userRef;
    @Index
    private String name;
    private boolean isPublic;
    @Index
    private String desc;
    private Date addDate;
    private Date updateDate;
    @Index
    private List<String> tags;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentationId() {
        return presentationId;
    }

    public void setPresentationId(String presentationId) {
        this.presentationId = presentationId;
    }

    public User getUserRef() {
        return userRef.get();
    }

    public void setUserRef(User userRef) {
        this.userRef = Ref.create(userRef);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
