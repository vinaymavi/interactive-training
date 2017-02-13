package entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by vku131 on 1/20/17.
 */
public class Address {
    private String address;
    private String city;
    private String country;
    //TODO need to include GEO coordinates.


    public Address() {
    }

    public Address(String address, String city) {
        this.address = address;
        this.city = city;
    }

    public Address(String address, String city, String country) {
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
