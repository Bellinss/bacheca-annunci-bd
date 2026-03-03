package it.uniroma2.dicii.bd.model.domain;

public class Ad {
    int idAd;
    String title;
    Float amount;
    String description;
    Category category;
    User user;
    TypeAd status;

    public Ad(String title, Float amount, String description, User user) {
        this.title = title;
        this.amount = amount;
        this.description= description;
        this.user = user;
    }

    public void setIdAd(int idAd) {
        this.idAd = idAd;
    }
    public TypeAd getStatus() {
        return status;
    }
    public String getTitle() {
        return title;
    }
    public void setStatus(int status) {
        this.status = TypeAd.fromInt(status);
    }
    public Float getAmount() {
        return amount;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public Category getCategory() {
        return category;
    }

    public void setStatus(TypeAd status) {
        this.status = status;
    }
    public User getUser() {
        return user;
    }
    public int getIdAd() {
        return idAd;
    }
}
