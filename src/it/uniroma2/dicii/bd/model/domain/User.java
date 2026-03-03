package it.uniroma2.dicii.bd.model.domain;

import java.sql.Date;
import java.util.List;

public class User {
    private String username;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String residentialAddress;
    private String billingAddress;
    private List<Ad> followAds;
    private TypeContact typePrefContact;
    private String prefContactDetails;
    private List<ContactNotPreferred> contactNotPreferred;
    private List<Notification> notifications;

    public User(String name, String surname, Date dateOfBirth, String residentialAddress, String billingAddress,TypeContact typeAddress, String prefContact) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.residentialAddress = residentialAddress;
        this.billingAddress = billingAddress;
        this.typePrefContact =typeAddress;
        this.prefContactDetails = prefContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public TypeContact getTypePrefContact() {
        return typePrefContact;
    }

    public void setTypePrefContact(TypeContact typePrefContact) {
        this.typePrefContact = typePrefContact;
    }

    public String getPrefContactDetails() {
        return prefContactDetails;
    }

    public void setPrefContactDetails(String prefContactDetails) {
        this.prefContactDetails = prefContactDetails;
    }
    public User(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public List<Ad> getFollowAds() {
        return followAds;
    }
    public void setFollowAds(List<Ad> followAds) {
        this.followAds = followAds;
    }

    public void setFollowAdId(int id) {
        this.followAds.get(0).setIdAd(id);
    }
    public List<ContactNotPreferred> getContactNotPreferred() {
        return contactNotPreferred;
    }

    public void setContactNotPreferred(List<ContactNotPreferred> contactNotPreferred) {
        this.contactNotPreferred = contactNotPreferred;
    }
    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surname;
    }
}
