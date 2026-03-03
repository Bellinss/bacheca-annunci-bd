package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Notification;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListNotificationProcedureDAO implements GenericProcedureDAO <List<Notification>>{
    private static ListNotificationProcedureDAO instance = null;
    private ListNotificationProcedureDAO(){
        //Singleton
    }
    public static ListNotificationProcedureDAO getInstance(){
        if(instance == null){
            instance = new ListNotificationProcedureDAO();
        }
        return instance;
    }
    @Override
    public List<Notification> execute(Object... params) throws DAOException, SQLException {
        List<Notification> notifications = new ArrayList<>();
        User user = (User) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call lista_notifiche(?)}");
            cs.setString(1, user.getUsername());
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    Notification notification = new Notification(rs.getInt(1), rs.getDate(3),
                            rs.getString(4));
                    notifications.add(notification);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error ListNote: " + e.getMessage());
        }
        return notifications;
    }
}
