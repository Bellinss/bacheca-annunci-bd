package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Comment;
import java.sql.*;

public class WriteCommentProcedureDAO implements GenericProcedureDAO <Comment>{
    private static WriteCommentProcedureDAO instance = null;

    private WriteCommentProcedureDAO(){
        //Singleton
    }
    public static WriteCommentProcedureDAO getInstance(){
        if(instance == null){
            instance = new WriteCommentProcedureDAO();
        }
        return instance;
    }
    @Override
    public Comment execute(Object... params) throws DAOException, SQLException {
        Comment comment = (Comment) params[0];
        int idAd = (int)params[1];
        try {

            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call invia_commento(?, ?, ?)}");
            cs.setString(1, comment.getText());
            cs.setInt(2, idAd);
            cs.registerOutParameter(3, Types.INTEGER);
            cs.execute();
            comment.setIdComment(cs.getInt(3));
        } catch (SQLException e) {
            throw new DAOException("Error write a comment: " + e.getMessage());
        }
        return comment;
    }
}
