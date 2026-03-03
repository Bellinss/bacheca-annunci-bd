package it.uniroma2.dicii.bd.model.dao;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.domain.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateReportProcedureDAO implements GenericProcedureDAO<Report>{
    private static GenerateReportProcedureDAO instance = null;
    private GenerateReportProcedureDAO(){
        //Singleton
    }
    public static GenerateReportProcedureDAO getInstance(){
        if(instance == null){
            instance = new GenerateReportProcedureDAO();
        }
        return instance;
    }
    @Override
    public Report execute(Object... params) throws DAOException{
        User seller = (User) params[0];
        Report report = null;
        try {
            Connection conn = ConnectionFactory.getConnection();
            CallableStatement cs = conn.prepareCall("{call genera_report(?)}");
            cs.setString(1, seller.getUsername());
            boolean status = cs.execute();
            if(status) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    report = new Report(seller, rs.getDate(2), rs.getFloat(3));
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error generating a report: " + e.getMessage());
        }
        return report;
    }
}