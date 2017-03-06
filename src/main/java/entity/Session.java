package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vku131 on 1/20/17.
 */
@Entity(name = "session")
public class Session {
    @Id
    private Long id;
    @Index
    private String sessionId;
    @Index
    private String name;
    private String desc;
    private Address address;
    private EmailAddress emailAddress;
    private Mobile mobile;
    @Index
    private Boolean isLive;
    @Index
    private Boolean isEnd;
    @Index
    private List<User> audience;
    @Parent
    @Load
    private Ref<Presentation> presentationRef;
    private Date addDate;
    private Date updateDate;
    private Date liveDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public Boolean getLive() {
        return isLive;
    }

    public void setLive(Boolean live) {
        isLive = live;
    }

    public Boolean getEnd() {
        return isEnd;
    }

    public void setEnd(Boolean end) {
        isEnd = end;
    }

    public Presentation getPresentationRef() {
        return presentationRef.get();
    }

    public void setPresentationRef(Presentation presentationRef) {
        this.presentationRef = Ref.create(presentationRef);
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

    public Date getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(Date liveDate) {
        this.liveDate = liveDate;
    }

    public List<User> getAudience() {

        return audience;
    }

    public void setAudience(List<User> audience) {

        this.audience = audience;
    }
}
