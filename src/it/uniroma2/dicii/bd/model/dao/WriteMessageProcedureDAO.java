package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.*;
import java.sql.*;

public class WriteMessageProcedureDAO implements GenericProcedureDAO <Boolean>{
    private static WriteMessageProcedureDAO instance = null;

    private WriteMessageProcedureDAO() {
        //Singleton
    }
    public static WriteMessageProcedureDAO getInstance(){
        if(instance == null){
            instance = new WriteMessageProcedureDAO();
        }
        return instance;
    }
    @Override
    public Boolean execute(Object... params) throws DAOException{

        User buyer = (User) params[0];
        User seller = (User) params[1];
        Message text = (Message) params[2];

        try {
            Connection conn = ConnectionFactory.getConnection();    // ottiene la connessione al database
            CallableStatement cs = conn.prepareCall("{call invia_messaggio(?,?,?,?,?,?,?)}");   // chiama la stored procedure invia_messaggio
            cs.setString(1, buyer.getUsername());
            cs.setString(2, seller.getUsername());
            cs.setString(3, text.getText());
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.INTEGER);
            cs.registerOutParameter(6, Types.DATE);
            cs.registerOutParameter(7, Types.TIME);
            cs.execute();

        } catch (SQLException e) {
            throw new DAOException("Error write a message: " + e.getMessage());
        }
        return true;
    }
}
