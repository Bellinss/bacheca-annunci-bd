package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.Category;
import it.uniroma2.dicii.bd.model.domain.CategoryList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowCategoriesProcedureDAO implements GenericProcedureDAO<CategoryList> {
    CategoryList categoryList = new CategoryList();
    private static ShowCategoriesProcedureDAO instance = null;
    private ShowCategoriesProcedureDAO(){
        //Singleton
    }
    public static ShowCategoriesProcedureDAO getInstance(){
        if(instance == null){
            instance = new ShowCategoriesProcedureDAO();
        }
        return instance;
    }
    @Override
    public CategoryList execute(Object... params) throws DAOException {
        CategoryList categoryList = new CategoryList();
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call visualizza_categoria()}");
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    categoryList.addCategories(new Category(rs.getString(1), rs.getString(2)));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error CategoryList: " + e.getMessage());
        }
        return categoryList;
    }
}