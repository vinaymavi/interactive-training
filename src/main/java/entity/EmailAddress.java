package entity;

import com.google.appengine.api.datastore.Email;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vku131 on 1/20/17.
 */
public class EmailAddress {
    @Index
    private Email email;
    private String type;

    public EmailAddress() {

    }

    public EmailAddress(Email email) {
        this.email = email;
    }

    public EmailAddress(Email email, String type) {
        this.email = email;
        this.type = type;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
