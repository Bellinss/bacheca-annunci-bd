package it.uniroma2.dicii.bd.model.domain;

import java.util.ArrayList;
import java.util.List;

public class NoteList {
    private List<Note> notes = new ArrayList<>();
    public int getSize(){
        return notes.size();
    }
    public List<Note> getNotes() {
        return notes;
    }
    public void addNote(Note note) {
        this.notes.add(note);

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Note note : notes) {
            sb.append(note.getText() + "\n");
        }
        return sb.toString();
    }
}
