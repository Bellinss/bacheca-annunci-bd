package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListAdProcedureDAO implements GenericProcedureDAO <AdList>{
    private static ListAdProcedureDAO instance = null;
    private ListAdProcedureDAO(){
        //Singleton
    }
    public static ListAdProcedureDAO getInstance(){
        if(instance == null){
            instance = new ListAdProcedureDAO();
        }
        return instance;
    }
    @Override
    public AdList execute(Object... params) throws DAOException{
        AdList adList = new AdList();
        try {
            Connection conn = ConnectionFactory.getConnection();    // ottiene la connessione al database
            // chiama la stored procedure lista_annunci
            CallableStatement cs = conn.prepareCall("{call lista_annunci()}");
            boolean status = cs.execute();  // esegue la stored procedure
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    Ad ad = new Ad(rs.getString(2), rs.getFloat(3), rs.getString(4),
                            new User(rs.getString(6)));
                    ad.setIdAd(rs.getInt(1));
                    ad.setStatus(TypeAd.valueOf(rs.getString(5).toUpperCase()));
                    ad.setCategory(new Category(rs.getString(7)));
                    adList.addAd(ad);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error AdList: " + e.getMessage());
        }
        return adList;
    }
}
