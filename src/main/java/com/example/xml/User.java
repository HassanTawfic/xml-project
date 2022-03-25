package com.example.xml;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

//@JsonPropertyOrder({"Name","Phone","Address","EMail"})
@JacksonXmlRootElement(localName = "users")
public class User {
    //@JsonProperty("Name")
    @JacksonXmlProperty(localName = "name")
    private String name;
    //@JsonProperty("Phone")
    @JacksonXmlProperty(localName = "phone")
    private String phone;
    //@JsonProperty("Address")
    @JacksonXmlProperty(localName = "address")
    private String address;
    //@JsonProperty("EMail")
    @JacksonXmlProperty(localName = "email")
    private String email;

    public  User(){}

    public User(String name, String phone, String address, String EMail) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = EMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEMail() {
        return email;
    }

    public void setEMail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
