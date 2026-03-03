package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.model.domain.Credentials;
import it.uniroma2.dicii.bd.model.domain.User;
import it.uniroma2.dicii.bd.view.ApplicationView;

public class ApplicationController implements Controller {
    Credentials cred;
    User user;
    @Override
    public void start() {
        while (true) {
            int choice;
            choice = ApplicationView.showApplication();
            switch (choice) {
                case 1 -> registration();
                case 2 -> login();
                default -> throw new RuntimeException("Invalid choice");
            }
        }
    }

    private void registration() {
        RegistrationController registrationController = new RegistrationController();
        registrationController.start();
        user = registrationController.getUser();
        cred = registrationController.getCred();
        new UserController().start(cred);
    }

    private void login() {
        LoginController loginController = new LoginController();
        loginController.start();
        cred = loginController.cred;

        if (cred == null || cred.getRole() == null) {
            System.out.println("Credenziali non valide. Torno al menu.");
            return; // rientra nel loop dello start()
        }

        switch (cred.getRole()) {

            case UTENTE -> new UserController().start(cred);
            case AMMINISTRATORE -> new AdministratorController().start(cred);
        }
    }
}
