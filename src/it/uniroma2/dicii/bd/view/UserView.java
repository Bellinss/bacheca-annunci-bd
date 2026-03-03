package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.model.domain.*;
import it.uniroma2.dicii.bd.model.utils.TablePrinter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserView {

    public static int showMenu(Credentials credentials) throws IOException {
        System.out.println("*********************************");
        System.out.println("*           DASHBOARD          *");
        System.out.println("*********************************\n");
        System.out.println("* Benvenuto " + credentials.getUsername() + "! *");
        System.out.println("* Cosa potrei fare per te? *\n");
        System.out.println("1 - Crea un annuncio");
        System.out.println("2 - Lista gli annunci ");
        System.out.println("3 - Visualizza un annuncio");
        System.out.println("4 - Scrivi un messaggio");
        System.out.println("5 - Visualizza una conversazione");
        System.out.println("6 - Visualizza annunci seguiti");
        System.out.println("7 - Esci");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Inserisci la scelta: ");
            String line = input.nextLine();
            if (line == null) {
                System.out.println("Input non valido. Riprova.");
                continue;
            }
            line = line.trim();
            if (line.isEmpty()) {
                System.out.println("La scelta non può essere vuota. Inserisci un numero da 1 a 7.");
                continue;
            }
            try {
                choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= 7) {
                    break;
                }
                System.out.println("Opzione non valida. Inserisci un numero da 1 a 7.");
            } catch (NumberFormatException e) {
                System.out.println("Devi inserire un numero valido (1-7).");
            }
        }
        return choice;
    }

    public static Ad createAd(User user) throws IOException {
        Scanner input = new Scanner(System.in);
        float amount = 0;
        boolean valido = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Titolo: ");
        String title = reader.readLine();
        while (!valido) {
            System.out.println("Importo: ");
            try {
                amount = input.nextFloat();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: this import is not valid");
                input.nextLine();
            }
        }
        System.out.println("Descrizione: ");
        String description = reader.readLine();
        return new Ad(title, amount, description, user);
    }

    public static void showCategory(CategoryList categoryList){
        System.out.println("--- CATEGORIE ---");
        String categories = categoryList.printTreeCategories();
        System.out.println(categories);
    }

    public static Category selectCategory(CategoryList categoryList) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Si prega di selezionare una categoria (ID o Nome): ");
            String input = reader.readLine();
            if (input == null) input = "";
            input = input.trim();

            if (input.isEmpty()) {
                System.out.println("L'input non può essere vuoto. Riprova.");
                continue;
            }

            for (Category c : categoryList.getCategories()) {
                String id = c.getIdCategory();
                if (input.equals(id)) {
                    return c;
                }
            }

            for (Category c : categoryList.getCategories()) {
                String name = c.getName();
                if (name != null && input.equalsIgnoreCase(name.trim())) {
                    return c;
                }
            }

            System.out.println("Categoria \"" + input + "\" non trovata. Riprova selezionando un ID o un Nome valido.");
        }
    }

    public static int selectAd(){
        Scanner input = new Scanner(System.in);
        int code;
        while (true) {
            System.out.print("Inserire codice annuncio: ");
            String line = input.nextLine();
            if (line == null) {
                System.out.println("Input non valido. Riprova.");
                continue;
            }
            line = line.trim();
            if (line.isEmpty()) {
                System.out.println("Il codice non può essere vuoto. Riprova.");
                continue;
            }
            try {
                code = Integer.parseInt(line);
                if (code <= 0) {
                    System.out.println("Inserisci un numero intero positivo.");
                    continue;
                }
                return code;
            } catch (NumberFormatException e) {
                System.out.println("Devi inserire un numero intero valido.");
            }
        }
    }
    public static void showAd(Ad ad) {
        TablePrinter tablePrinter = new TablePrinter();
        tablePrinter.setShowVerticalLines(true);
        if (ad == null) {
            System.out.println("\nNessun annuncio inserito! ");
        } else {
            tablePrinter.setHeaders("Titolo", "Importo", "Descrizione", "Stato", "Username", "Categoria" );

            String stato = (ad.getStatus() != null) ? ad.getStatus().toString() : "N/A";

            tablePrinter.addRow(
                    ad.getTitle(),
                    ad.getAmount().toString(),
                    ad.getDescription(),
                    stato,
                    ad.getUser().getUsername(),
                    (ad.getCategory() != null ? ad.getCategory().getName() : "N/A")
            );
            tablePrinter.print();
        }
    }
    public static void showListAd(AdList adList) {
        TablePrinter tablePrinter = new TablePrinter();
        tablePrinter.setShowVerticalLines(true);
        if (adList == null) {
            System.out.println("\nNessun annuncio inserito! ");
        } else {
            tablePrinter.setHeaders("Codice","Titolo", "Venditore");
            for(int i=0; i<adList.getSize(); i++){
                tablePrinter.addRow(String.valueOf(adList.getAds().get(i).getIdAd()), adList.getAds().get(i).getTitle(),
                        adList.getAds().get(i).getUser().getUsername());
            }

            tablePrinter.print();

        }
    }
    public static String listMessages(){
        Scanner input = new Scanner(System.in);
        System.out.println("Inserire username: ");
        return input.nextLine();
    }
    public static Conversation writeMessage(User user) {
        Scanner input = new Scanner(System.in);
        System.out.println("Inserire username: ");
        String seller = input.nextLine();
        System.out.println("Inserire messaggio: ");
        String msg = input.nextLine();
        return new Conversation( user, new User(seller), new Message(msg));
    }

    public static int second_menu() {
        System.out.println("1 - Indica l'oggetto come venduto");
        System.out.println("2 - Visualizza commenti");
        System.out.println("3 - Scrivi un commento");
        System.out.println("4 - Segui un annuncio");
        System.out.println("5 - Visualizza nota");
        System.out.println("6 - Crea nota");
        System.out.println("7 - Ritorna al menu precedente ");

        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Inserisci la scelta: ");
            String line = input.nextLine();
            if (line == null) {
                System.out.println("Input non valido. Riprova.");
                continue;
            }
            line = line.trim();
            if (line.isEmpty()) {
                System.out.println("La scelta non può essere vuota. Inserisci un numero da 1 a 7.");
                continue;
            }
            try {
                choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= 7) {
                    break;
                }
                System.out.println("Opzione non valida. Inserisci un numero da 1 a 7.");
            } catch (NumberFormatException e) {
                System.out.println("Devi inserire un numero valido (1-7).");
            }
        }
        return choice;
    }

    public static Comment writeComment() {
        Scanner input = new Scanner(System.in);
        System.out.println("Inserire commento: ");
        String cmt = input.nextLine();
        return new Comment(cmt);
    }

    public static Note writeNote() {
        Scanner input = new Scanner(System.in);
        System.out.println("Inserire nota: ");
        String note = input.nextLine();
        return new Note( note);
    }
    public static boolean printNotificationAndDelete(List<Notification> notifications){
        for(int i=0; i<notifications.size(); i++){
            System.out.println(notifications.get(i).getDate() + " \uD83D\uDD14" + notifications.get(i).getText());
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Vuoi eliminare tutte le notifiche? (y/n): ");
        String x = input.nextLine();
        if(x.equals("y")){
            return true;
        }
        return false;
    }
}
