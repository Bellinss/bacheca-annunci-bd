package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.ContactNotPreferred;
import it.uniroma2.dicii.bd.model.domain.Credentials;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AddAddressNotPreferredProcedureDAO implements GenericProcedureDAO<Boolean>{
    private static AddAddressNotPreferredProcedureDAO instance = null;
    private AddAddressNotPreferredProcedureDAO(){
        //Singleton
    }
    public static AddAddressNotPreferredProcedureDAO getInstance(){
        if(instance == null){
            instance = new AddAddressNotPreferredProcedureDAO();
        }
        return instance;
    }
    @Override
    public Boolean execute(Object... params) throws DAOException{
        ContactNotPreferred contactNotPreferred = (ContactNotPreferred) params[0];
        Credentials credentials = (Credentials) params[1];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call registra_recapito_non_preferito(?,?,?)}");
            cs.setString(1, contactNotPreferred.getContact());
            cs.setString(2, contactNotPreferred.getTypeContact().name());
            cs.setString(3, credentials.getUsername());
            cs.executeQuery();
        } catch (SQLException e) {
            throw new DAOException("Error addAdressNotPreferred: " + e.getMessage());
        }
        return true;
    }
}