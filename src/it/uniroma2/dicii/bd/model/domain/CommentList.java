package it.uniroma2.dicii.bd.model.domain;
import java.util.ArrayList;
import java.util.List;

public class CommentList {
    private final List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("** Comments **" + "\n");
        for(Comment comment : comments) {
            sb.append(comment.getText()).append("\n");
        }
        return sb.toString();
    }
}
