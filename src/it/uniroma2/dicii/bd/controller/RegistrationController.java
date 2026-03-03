package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.dao.AddAddressNotPreferredProcedureDAO;
import it.uniroma2.dicii.bd.model.dao.RegisterProcedureDAO;
import it.uniroma2.dicii.bd.model.domain.Credentials;
import it.uniroma2.dicii.bd.model.domain.User;
import it.uniroma2.dicii.bd.view.RegisterView;
import java.io.IOException;

public class RegistrationController implements Controller {
    private Credentials cred = null;
    private User user;

    @Override
    public void start() {
        Boolean check1, check2 = false;
        try {
            cred = RegisterView.register();
            user = RegisterView.insertInfo();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        if(user.getContactNotPreferred() == null){
            try {
                RegisterProcedureDAO register = RegisterProcedureDAO.getInstance();
                check1 = register.execute(cred, user);
            } catch(DAOException e) {
                throw new RuntimeException(e);
            }
            if(check1){
                System.out.println("Registrazione avvenuta con successo!");
            }
        }else {
            try {
                RegisterProcedureDAO register = RegisterProcedureDAO.getInstance();
                check1 = register.execute(cred, user);

            } catch(DAOException e) {
                throw new RuntimeException(e);
            }
            try{

                AddAddressNotPreferredProcedureDAO addAddressNotPref = AddAddressNotPreferredProcedureDAO.getInstance();
                for(int i=0; i<user.getContactNotPreferred().size(); i++)
                    check2 = addAddressNotPref.execute(user.getContactNotPreferred().get(i),cred);
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            if(check1 & check2){
                System.out.println("Registrazione avvenuta con successo!");
            }
        }

    }

    public Credentials getCred() {
        return cred;
    }
    public User getUser(){ return user; }
}
