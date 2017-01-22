package entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vku131 on 1/20/17.
 */
public class Mobile {
    private String countryCode;
    @Index
    private String number;
    private String type;

    public Mobile() {
    }

    public Mobile(String number) {
        this.number = number;
    }

    public Mobile(String countryCode, String number, String type) {
        this.countryCode = countryCode;
        this.number = number;
        this.type = type;
    }

    public Mobile(String countryCode, String number) {
        this.countryCode = countryCode;
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
