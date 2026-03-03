package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListFollowedAdProcedureDAO implements GenericProcedureDAO <AdList>{
    private static ListFollowedAdProcedureDAO instance = null;
    private ListFollowedAdProcedureDAO(){
        //Singleton
    }
    public static ListFollowedAdProcedureDAO getInstance(){
        if(instance == null){
            instance = new ListFollowedAdProcedureDAO();
        }
        return instance;
    }
    @Override
    public AdList execute(Object... params) throws DAOException {
        AdList adList = new AdList();
        User user = (User) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call lista_annunci_seguiti(?)}");
            cs.setString(1, user.getUsername());
            boolean status = cs.execute();
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
            throw new DAOException("Error AdListFollowed: " + e.getMessage());
        }
        return adList;
    }
}
