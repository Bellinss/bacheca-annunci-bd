package it.uniroma2.dicii.bd.model.domain;

public class Note {
    int idNote;
    String text;

    public Note(String text) {
        this.text = text;
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
