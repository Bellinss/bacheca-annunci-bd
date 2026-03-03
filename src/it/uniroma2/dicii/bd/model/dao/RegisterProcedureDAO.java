package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Credentials;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.*;

public class RegisterProcedureDAO implements GenericProcedureDAO <Boolean> {
    private static RegisterProcedureDAO instance = null;

    private RegisterProcedureDAO() {
        //Singleton
    }

    public static RegisterProcedureDAO getInstance() {
        if (instance == null) {
            instance = new RegisterProcedureDAO();
        }
        return instance;
    }

    @Override
    public Boolean execute(Object... params) throws DAOException {
        Credentials cred = (Credentials) params[0];
        User user = (User) params[1];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call registra_utente(?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, cred.getUsername());
            cs.setString(2, cred.getPassword());
            cs.setString(3, user.getName());
            cs.setString(4, user.getSurname());
            cs.setDate(5, user.getDateOfBirth());
            cs.setString(6, user.getResidentialAddress());
            cs.setString(7, user.getBillingAddress());
            cs.setString(8, user.getTypePrefContact().name());
            cs.setString(9, user.getPrefContactDetails());
            cs.executeQuery();

        } catch (SQLException e) {
            throw new DAOException("Error: " + e.getMessage());
        }
        return true;
    }
}