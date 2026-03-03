package it.uniroma2.dicii.bd.model.domain;

public class ContactNotPreferred {
    Enum<TypeContact> typeContact;
    String contact;
    public ContactNotPreferred(Enum<TypeContact> typeContact, String contact) {
        this.typeContact = typeContact;
        this.contact = contact;
    }
    public Enum<TypeContact> getTypeContact() {
        return typeContact;
    }

    public String getContact() {
        return contact;
    }
}
