package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewMessagesProcedureDAO implements GenericProcedureDAO <Conversation>{
    private static ViewMessagesProcedureDAO instance = null;

    private ViewMessagesProcedureDAO(){
        //Singleton
    }
    public static ViewMessagesProcedureDAO getInstance(){
        if(instance == null){
            instance = new ViewMessagesProcedureDAO();
        }
        return instance;
    }
    @Override
    public Conversation execute(Object... params) throws DAOException, SQLException {
        User user = (User) params[0];
        User seller = (User)params[1];
        Conversation conv = null;
        Message m = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call lista_messaggi(?,?)}");
            cs.setString(1, user.getUsername());
            cs.setString(2, seller.getUsername());
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if(rs.next()) {
                    m = getMessage(user, seller, rs);
                }
                conv = new Conversation(user,seller, m);
                while (rs.next()){
                    m = getMessage(user, seller, rs);
                    conv.addMessages(m);
                }

            }
        } catch (SQLException e) {
            throw new DAOException("Error ViewMessages: " + e.getMessage());
        }
        return conv;
    }

    private Message getMessage(User user, User seller, ResultSet rs) throws SQLException {
        Message m;
        if(rs.getString(1).equals(user.getUsername())){
            m = new Message(user, rs.getDate(3), rs.getTime(4), rs.getString(5));
            m.setId(rs.getInt(2));
        }else{
            m = new Message(seller, rs.getDate(3), rs.getTime(4), rs.getString(5));
            m.setId(rs.getInt(2));
        }
        return m;
    }
}
