package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AddCategoryProcedureDAO implements GenericProcedureDAO<Boolean>{
    private static AddCategoryProcedureDAO instance = null;

    private AddCategoryProcedureDAO(){
        //Singleton
    }
    public static AddCategoryProcedureDAO getInstance(){
        if(instance == null){
            instance = new AddCategoryProcedureDAO();
        }
        return instance;
    }
    @Override
    public Boolean execute(Object... params) throws DAOException{
        Category category = (Category) params[0];
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call aggiungi_categoria(?,?)}");
            cs.setString(1, category.getIdCategory());
            cs.setString(2, category.getName());
            cs.execute();
        } catch (SQLException e) {
            throw new DAOException("Error adding category: " + e.getMessage());
        }
        return true;
    }
}
