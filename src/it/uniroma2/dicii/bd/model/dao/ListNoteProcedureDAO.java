package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListNoteProcedureDAO implements GenericProcedureDAO <NoteList>{
    private static ListNoteProcedureDAO instance = null;
    private ListNoteProcedureDAO(){
        //Singleton
    }
    public static ListNoteProcedureDAO getInstance(){
        if(instance == null){
            instance = new ListNoteProcedureDAO();
        }
        return instance;
    }
    @Override
    public NoteList execute(Object... params) throws DAOException {
        NoteList noteList = new NoteList();
        int idAnnuncio = (int) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call visualizza_note(?)}");
            cs.setInt(1, idAnnuncio);
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    Note note = new Note(rs.getString(2));
                    note.setIdNote(rs.getInt(1));
                    noteList.addNote(note);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error ListNote: " + e.getMessage());
        }
        return noteList;
    }
}