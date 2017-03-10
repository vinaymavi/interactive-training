package entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * User Entity.
 */
@Entity(name = "user")
public class User {
    @Id
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    // only use for indexing.
    @Index
    private String fullName;
    @Index
    private String fbId;
    @Index
    private String senderId;
    @Index
    private String gender;
    private Date addDate;
    private Date updateDate;
    @Index
    private Boolean status;
    private URL profileImage;
    private Address address;
    private Mobile mobile;
    private EmailAddress emailAddress;
    private boolean isRegistered;
    @Index
    private Set<String> sessionList;

    public User() {
        this.isRegistered = false;
    }

    public User(String fbId) {
        this.fbId = fbId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public URL getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(URL profileImage) {
        this.profileImage = profileImage;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public Set<String> getSessionList() {
        return sessionList;
    }

    public void setSessionList(Set<String> sessionList) {
        this.sessionList = sessionList;
    }

    @Override
    public int hashCode() {
        return this.id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        if (this.getSenderId().equals(((User) obj).getSenderId())) {
            return true;
        }
        return false;
    }
}
