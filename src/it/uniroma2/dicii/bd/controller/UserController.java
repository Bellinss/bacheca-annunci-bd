package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DAOException;
import it.uniroma2.dicii.bd.model.dao.*;
import it.uniroma2.dicii.bd.model.domain.*;
import it.uniroma2.dicii.bd.view.ApplicationView;
import it.uniroma2.dicii.bd.view.UserView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserController implements ControllerSession{
    private User user;
    private Ad ad = null;

    @Override
    public void start(Credentials credentials) {
        List<Notification> notificationList;
        boolean check = false;
        try {
            ConnectionFactory.changeRole(Role.UTENTE);
            ViewUserProcedureDAO obtainUser = ViewUserProcedureDAO.getInstance();
            user = obtainUser.execute(credentials.getUsername());
            ListNotificationProcedureDAO listNotification = ListNotificationProcedureDAO.getInstance();
            notificationList = listNotification.execute(user);
        } catch(SQLException | DAOException e) {
            throw new RuntimeException(e);
        }

        if(!notificationList.isEmpty()){
            check=UserView.printNotificationAndDelete(notificationList);
        }
        if(check){
            DeleteNotificationProcedureDAO deleteNotification = DeleteNotificationProcedureDAO.getInstance();
            try {
                deleteNotification.execute(user);
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        }

        chooseOperation(credentials);
    }
    private void chooseOperation(Credentials credentials){
        while(true) {
            int choice;
            try {
                choice = UserView.showMenu(credentials);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            switch (choice) {
                case 1 -> newAd();
                case 2 -> listAd();
                case 3 -> viewAd(credentials);
                case 4 -> writeMessage();
                case 5 -> viewMessages();
                case 6 -> listFollowedAd();
                case 7 -> System.exit(0);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
    }
    private void listFollowedAd() {
        ListFollowedAdProcedureDAO followAd = ListFollowedAdProcedureDAO.getInstance();
        AdList adList = null;
        try {
            adList = followAd.execute(user);
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
        if (adList != null && adList.getSize() > 0) {
            UserView.showListAd(adList);
        }else {
            System.out.println("Nessun annuncio seguito");
        }

    }

    private void viewMessages() {
        User seller = new User(UserView.listMessages());
        ViewMessagesProcedureDAO viewMessages = ViewMessagesProcedureDAO.getInstance();
        Conversation conversation = null;
        try {
            conversation = viewMessages.execute(user, seller);
        } catch (DAOException | SQLException e) {
            ApplicationView.printError(e);
        }
        if(conversation != null){
            System.out.println(conversation);
        }else{
            System.out.println("Nessuna conversazione trovata.");
        }
    }

    private void writeMessage() {
        WriteMessageProcedureDAO writeMessage = WriteMessageProcedureDAO.getInstance();
        Conversation conv = UserView.writeMessage(user);
        Boolean check = false;
        try {
            check = writeMessage.execute(user, conv.getSeller(), conv.getMessageLists().get(0));
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
        if(check){
            System.out.println("Messaggio scritto con successo!");
        }
    }

    private void listAd() {
        AdList adList = null;
        try {
            ListAdProcedureDAO listAd = ListAdProcedureDAO.getInstance();
            adList = listAd.execute(user);
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
        UserView.showListAd(adList);
    }

    private void newAd(){

        Category category;
        try {
            ad = UserView.createAd(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            ShowCategoriesProcedureDAO showCategories = ShowCategoriesProcedureDAO.getInstance();
            CategoryList categoryList;

            categoryList = showCategories.execute();
            UserView.showCategory(categoryList);

            category = UserView.selectCategory(categoryList);
            ad.setCategory(category);
        } catch (IOException | DAOException e) {
            ApplicationView.printError(e);
            return;
        }
        try {
            AddAdProcedureDAO addAd = AddAdProcedureDAO.getInstance();
            ad = addAd.execute(ad.getTitle(), ad.getAmount(), ad.getDescription(), user, ad.getCategory());
        } catch(DAOException e) {
            ApplicationView.printError(e);
            return;
        }
        UserView.showAd(ad);
    }

    private void viewAd(Credentials credentials) {
        int id = UserView.selectAd();
        try {
            ViewAdProcedureDAO viewAd = ViewAdProcedureDAO.getInstance();
            ad = viewAd.execute(id);
        } catch (DAOException e) {
            ApplicationView.printError(e);
            chooseOperation(credentials);
        }
        UserView.showAd(ad);
        while(true) {
            int choice;
            choice = UserView.second_menu();

            switch(choice) {
                case 1 -> adSold(id);
                case 2 -> listComments(id);
                case 3 -> writeComment(id);
                case 4 -> followAd(id);
                case 5 -> viewNote(id);
                case 6 -> createNote(id);
                case 7 -> chooseOperation(credentials);
                default -> throw new RuntimeException("Invalid choice");
            }
        }
    }

    private void adSold(int idAd) {
        Boolean check = false;
        AdSoldProcedureDAO adSold = AdSoldProcedureDAO.getInstance();
        try {
            check = adSold.execute(idAd, user);
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
        if(check){
            System.out.println("Annuncio venduto!");
        }
    }

    private void createNote(int idAd) {
        WriteNoteProcedureDAO writeNote = WriteNoteProcedureDAO.getInstance();
        Note note = UserView.writeNote();
        Boolean check = false;
        try {
            check=writeNote.execute(note, idAd, user);
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
        if(check)
            System.out.println("Nota scritta con successo");
    }

    private void viewNote(int idAd) {
        NoteList noteList = null;
        try {
            ListNoteProcedureDAO listNote = ListNoteProcedureDAO.getInstance();
            noteList = listNote.execute(idAd);
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
        if (noteList != null) {
            System.out.println(noteList);
        }else{
            System.out.println("\nNessuna nota");
        }
    }

    private void followAd(int idAd) {
        FollowAdProcedureDAO followAd = FollowAdProcedureDAO.getInstance();
        Boolean check = false;
        try {
            check = followAd.execute(user, idAd);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
        if(check)
            System.out.println("\nAnnuncio seguito correttamente!");
    }

    private void listComments(int idAd) {
        CommentList commentList = null;
        try {
            ListCommentsProcedureDAO listComments = ListCommentsProcedureDAO.getInstance();
            commentList = listComments.execute(idAd);
        } catch (DAOException e) {
            ApplicationView.printError(e);
        }
        if (commentList != null) {
            System.out.println(commentList);
        }else{
            System.out.println("\nNessun commento");
        }

    }

    private void writeComment(int idAd) {
        Comment comment = UserView.writeComment();
        WriteCommentProcedureDAO writeComment = WriteCommentProcedureDAO.getInstance();
        try {
            writeComment.execute(comment, idAd);
        } catch (DAOException | SQLException e) {
            ApplicationView.printError(e);
        }

    }


}
