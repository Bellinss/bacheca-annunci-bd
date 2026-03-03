package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class FollowAdProcedureDAO implements GenericProcedureDAO <Boolean>{

    private static FollowAdProcedureDAO instance = null;
    private FollowAdProcedureDAO(){
        //Singleton
    }
    public static FollowAdProcedureDAO getInstance(){
        if(instance == null){
            instance = new FollowAdProcedureDAO();
        }
        return instance;
    }
    @Override
    public Boolean execute(Object... params) throws DAOException{
        User user = (User) params[0];
        int id = (int) params[1];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call segui_annuncio(?,?)}");
            cs.setString(1, user.getUsername());
            cs.setInt(2, id);
            cs.execute();

        } catch (SQLException e) {
            throw new DAOException("Error FollowAd: " + e.getMessage());
        }
        return true;
    }
}
