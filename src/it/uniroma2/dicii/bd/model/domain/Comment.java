package it.uniroma2.dicii.bd.model.domain;

public class Comment {
    int idComment;
    String Text;
    private User owner;

    public Comment(String text) {
        Text = text;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }
    public String getText() {
        return Text;
    }
}
