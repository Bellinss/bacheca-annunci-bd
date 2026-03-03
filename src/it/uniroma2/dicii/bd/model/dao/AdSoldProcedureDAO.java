package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AdSoldProcedureDAO implements GenericProcedureDAO <Boolean>{
    private static AdSoldProcedureDAO instance = null;
    private AdSoldProcedureDAO(){
        //Singleton
    }
    public static AdSoldProcedureDAO getInstance(){
        if(instance == null){
            instance = new AdSoldProcedureDAO();
        }
        return instance;
    }
    @Override
    public Boolean execute(Object... params) throws DAOException {
        int id = (int)params[0];
        User seller = (User) params[1];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call oggetto_venduto(?, ?)}");
            cs.setInt(1, id);
            cs.setString(2, seller.getUsername());
            cs.executeQuery();

        } catch (SQLException e) {
            throw new DAOException("Error: " + e.getMessage());
        }
        return true;
    }
}
