package it.uniroma2.dicii.bd.model.domain;

public enum TypeContact {
    email(1),
    telefono(2),
    cellulare(3);
    private int id;
    private TypeContact(int id) {
        this.id = id;
    }
    public static TypeContact fromInt(int id) {
        for (TypeContact type : values()) {
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
