package com.neuSep17.dto;

public class Dealer {
    private String id;
    private String name;
    private String url;
    private String emailId;
    private String contactNumber;

    public Dealer() {}

    public Dealer(String id, String name, String url, String emailId, String contactNumber) {
        this.id=id; this.name=name; this.url=url; this.emailId = emailId; this.contactNumber = contactNumber;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getEmailId() { return emailId;}
    public String getContactNumber() { return contactNumber; }

    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

