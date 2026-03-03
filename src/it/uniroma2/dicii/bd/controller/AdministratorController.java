package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.dao.AddCategoryProcedureDAO;
import it.uniroma2.dicii.bd.model.dao.ConnectionFactory;
import it.uniroma2.dicii.bd.model.dao.GenerateReportProcedureDAO;
import it.uniroma2.dicii.bd.model.dao.ShowCategoriesProcedureDAO;
import it.uniroma2.dicii.bd.model.domain.*;
import it.uniroma2.dicii.bd.view.AdministratorView;
import it.uniroma2.dicii.bd.view.ApplicationView;

import java.io.IOException;
import java.sql.SQLException;

public class AdministratorController implements ControllerSession {

    User administrator;
    User user;
    @Override
    public void start(Credentials credentials) {

        try {
            ConnectionFactory.changeRole(Role.AMMINISTRATORE);
            administrator = new User(credentials.getUsername());
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        while(true) {
            int choice;
            choice = AdministratorView.showMenu();
            switch (choice) {
                case 1 -> generateReport();
                case 2 -> addCategory();
                case 3 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
    }

    private void addCategory() {
        AddCategoryProcedureDAO addCategory = AddCategoryProcedureDAO.getInstance();
        Category category;
        CategoryList categoryList;
        ShowCategoriesProcedureDAO showCategories = ShowCategoriesProcedureDAO.getInstance();
        try {
            categoryList = showCategories.execute();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(categoryList.toString());
        try {
            category = AdministratorView.insertCategory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            addCategory.execute(category);
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
    }

    private void generateReport() {
        GenerateReportProcedureDAO generateReport = GenerateReportProcedureDAO.getInstance();
        Report report = null;
        try {
            user = AdministratorView.insertReport();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            report=generateReport.execute(user);
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
        AdministratorView.showReport(report);
    }
}
