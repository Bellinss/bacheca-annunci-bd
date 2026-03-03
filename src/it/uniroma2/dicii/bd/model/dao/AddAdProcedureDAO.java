package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Ad;
import it.uniroma2.dicii.bd.model.domain.Category;
import it.uniroma2.dicii.bd.model.domain.User;
import java.sql.*;

public class AddAdProcedureDAO implements GenericProcedureDAO<Ad> {
    private static AddAdProcedureDAO instance = null;
    private AddAdProcedureDAO(){
        //Singleton
    }
    public static AddAdProcedureDAO getInstance(){
        if(instance == null){
            instance = new AddAdProcedureDAO();
        }
        return instance;
    }
    @Override
    public Ad execute(Object... params) throws DAOException{
        Ad ad;
        String title = (String) params[0];
        float amount = (float) params[1];
        String description = (String) params[2];
        User user = (User) params[3];
        Category category = (Category) params[4];
        int idAd;

        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call crea_annuncio(?,?,?,?,?,?)}");
            cs.setString(1, title);
            cs.setFloat(2, amount);
            cs.setString(3, description);
            cs.setString(4, user.getUsername());
            cs.setString(5, category.getName());
            cs.registerOutParameter(6, Types.INTEGER);
            cs.executeQuery();
            idAd = cs.getInt(6);
        } catch(SQLException e) {
            throw new DAOException("Error: " + e.getMessage());
        }
        ad = new Ad(title, amount,description, user);
        ad.setIdAd(idAd);
        ad.setStatus(1);
        ad.setCategory(category);
        return ad;
    }
}
