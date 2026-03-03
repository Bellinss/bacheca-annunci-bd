package it.uniroma2.dicii.bd.model.domain;

public enum TypeAd {
    ATTIVO(1),
    VENDUTO(2),
    RIMOSSO(3);
    private int id;
    private TypeAd(int id) {
        this.id = id;
    }
    public static TypeAd fromInt(int id) {
        for (TypeAd type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
