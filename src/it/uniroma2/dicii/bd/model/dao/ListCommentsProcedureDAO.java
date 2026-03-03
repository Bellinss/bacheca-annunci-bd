package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListCommentsProcedureDAO implements GenericProcedureDAO <CommentList>{
    private static ListCommentsProcedureDAO instance = null;

    private ListCommentsProcedureDAO(){
        //Singleton
    }
    public static ListCommentsProcedureDAO getInstance(){
        if(instance == null){
            instance = new ListCommentsProcedureDAO();
        }
        return instance;
    }
    @Override
    public CommentList execute(Object... params) throws DAOException{
        int idAnnuncio = (int) params[0];
        CommentList commentList = new CommentList();
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call lista_commenti(?)}");
            cs.setInt(1, idAnnuncio);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    Comment comment = new Comment(rs.getString(2));
                    comment.setIdComment(1);
                    commentList.addComment(comment);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error CommentList: " + e.getMessage());
        }
        return commentList;
    }
}
