package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.dao.LoginProcedureDAO;
import it.uniroma2.dicii.bd.model.domain.Credentials;
import it.uniroma2.dicii.bd.view.LoginView;

import java.io.IOException;

public class LoginController implements Controller {
    Credentials cred = null;

    @Override
    public void start() {
        while (true) {
            try {
                // 1) leggi credenziali in modo robusto dalla view
                cred = LoginView.authenticate();

                // 2) verifica col DAO
                LoginProcedureDAO login = LoginProcedureDAO.getInstance();
                Credentials found = login.execute(cred.getUsername(), cred.getPassword());

                // 3) controllo esito
                if (found == null || found.getRole() == null) {
                    System.out.println("Credenziali non valide.");
                    // chiedi se riprovare
                    if (!LoginView.askRetryLogin()) {
                        cred = null; // segnala al chiamante che non si è loggati
                        return;
                    }
                    continue;
                }

                // ok
                cred = found;
                return;

            } catch (DAOException e) {
                System.out.println("Errore di autenticazione: " + e.getMessage());
                try {
                    if (!LoginView.askRetryLogin()) {
                        cred = null;
                        return;
                    }
                } catch (IOException io) {
                    cred = null;
                    return;
                }
            } catch (IOException ioEx) {
                System.out.println("Errore di input. Riprovo...");
            }
        }
    }
}
