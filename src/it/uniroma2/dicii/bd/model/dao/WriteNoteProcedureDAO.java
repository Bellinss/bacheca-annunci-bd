package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Note;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class WriteNoteProcedureDAO implements GenericProcedureDAO <Boolean>{
    private static WriteNoteProcedureDAO instance = null;
    private WriteNoteProcedureDAO(){
        //Singleton
    }
    public static WriteNoteProcedureDAO getInstance(){
        if(instance == null){
            instance = new WriteNoteProcedureDAO();
        }
        return instance;
    }
    @Override
    public Boolean execute(Object... params) throws DAOException {
        Note note = (Note) params[0];
        int idAd = (int)params[1];
        User seller = (User) params[2];
        try {

            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call crea_nota(?,?,?,?)}");
            cs.setString(1, seller.getUsername());
            cs.setString(2, note.getText());
            cs.setInt(3, idAd);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.execute();
            note.setIdNote(cs.getInt(4));
        } catch (SQLException e) {
            throw new DAOException("Error write a note: " + e.getMessage());
        }
        return true;
    }
}
