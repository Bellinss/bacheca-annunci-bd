package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.TypeContact;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewUserProcedureDAO implements GenericProcedureDAO <User>{
    private static ViewUserProcedureDAO instance = null;

    private ViewUserProcedureDAO(){
        //Singleton
    }
    public static ViewUserProcedureDAO getInstance(){
        if(instance == null){
            instance = new ViewUserProcedureDAO();
        }
        return instance;
    }
    @Override
    public User execute(Object... params) throws DAOException, SQLException {
        String username= (String) params[0];
        User user= null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call visualizza_utente(?)}");
            cs.setString(1, username);
            boolean status = cs.execute();
            if(status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    user = new User(rs.getString(3), rs.getString(4), rs.getDate(5),
                            rs.getString(6), rs.getString(7), TypeContact.valueOf(rs.getString(8)), rs.getString(9));
                    user.setUsername(username);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error obtaining user: " + e.getMessage());
        }
        return user;
    }
}
