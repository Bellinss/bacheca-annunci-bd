package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteNotificationProcedureDAO implements GenericProcedureDAO<Boolean>{

    private static DeleteNotificationProcedureDAO instance = null;
    private DeleteNotificationProcedureDAO(){
        //Singleton
    }
    public static DeleteNotificationProcedureDAO getInstance(){
        if(instance == null){
            instance = new DeleteNotificationProcedureDAO();
        }
        return instance;
    }
    @Override
    public Boolean execute(Object... params) throws DAOException {
        User user = (User) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call elimina_notifiche(?)}");
            cs.setString(1, user.getUsername());
            cs.execute();
        } catch (SQLException e) {
            throw new DAOException("Error delete notifications: " + e.getMessage());
        }
        return true;
    }
}
