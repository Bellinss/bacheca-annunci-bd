package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.model.domain.Category;
import it.uniroma2.dicii.bd.model.domain.Report;
import it.uniroma2.dicii.bd.model.domain.User;
import it.uniroma2.dicii.bd.model.utils.TablePrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class AdministratorView {
    public static int showMenu(){
        System.out.println("*********************************");
        System.out.println("*       ADMIN DASHBOARD         *");
        System.out.println("*********************************\n");
        System.out.println("*** Cosa potrei fare per te? ***\n");
        System.out.println("1 - Genera report");
        System.out.println("2 - Aggiungi categoria");
        System.out.println("3 - Esci");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Inserisci una scelta: ");
            String line = input.nextLine();
            try {
                choice = Integer.parseInt(line.trim());
                if (choice >= 1 && choice <= 3) {
                    break;
                }
                System.out.println("Opzione invalida");
            } catch (NumberFormatException e) {
                System.out.println("Per favore inserisci un numero valido.");
            }
        }

        return choice;
    }
    public static User insertReport() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Username: ");
        String username = reader.readLine();
        return new User(username);
    }

    public static void showReport(Report report) {
        TablePrinter tablePrinter = new TablePrinter();
        tablePrinter.setShowVerticalLines(true);
        if (report != null) {
            tablePrinter.setHeaders("Venditore", "Data", "Percentuale Vendita");

            tablePrinter.addRow(report.getUser().getUsername(), report.getDate().toString(),
                    String.valueOf(report.getPercentage()));
            tablePrinter.print();

        }
    }

    public static Category insertCategory() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Path: ");
        String path = reader.readLine();
        System.out.println("Nome: ");
        String name = reader.readLine();
        return new Category(path, name);
    }
}
