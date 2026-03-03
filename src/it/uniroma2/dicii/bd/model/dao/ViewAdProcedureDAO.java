package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Ad;
import it.uniroma2.dicii.bd.model.domain.Category;
import it.uniroma2.dicii.bd.model.domain.TypeAd;
import it.uniroma2.dicii.bd.model.domain.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAdProcedureDAO implements GenericProcedureDAO<Ad>{

    private static ViewAdProcedureDAO instance = null;

    private ViewAdProcedureDAO(){
        //Singleton
    }
    public static ViewAdProcedureDAO getInstance(){
        if(instance == null){
            instance = new ViewAdProcedureDAO();
        }
        return instance;
    }
    @Override
    public Ad execute(Object... params) throws DAOException {
        int id = (int) params[0];
        Ad ad = null;
        try {

            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call visualizza_annuncio(?)}");
            cs.setInt(1, id);
            boolean status = cs.execute();
            if(status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    ad = new Ad(rs.getString(2), rs.getFloat(3), rs.getString(4),
                            new User(rs.getString(6)));
                    ad.setIdAd(rs.getInt(1));
                    ad.setStatus(TypeAd.valueOf(rs.getString(5).toUpperCase()));
                    ad.setCategory(new Category(rs.getString(7)));
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error view ad: " + e.getMessage());
        }
        return ad;
    }
}
