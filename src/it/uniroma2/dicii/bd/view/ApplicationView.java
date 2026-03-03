package it.uniroma2.dicii.bd.view;

import java.util.Scanner;

public class ApplicationView {
    public static int showApplication(){
        System.out.println("**********************************");
        System.out.println("* BACHECA ELETTRONICA DI ANNUNCI *");
        System.out.println("**********************************\n");
        System.out.println("1 - Registration\n");
        System.out.println("2 - Login\n");

        Scanner input = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.print("Inserisci la scelta: ");
            String line = input.nextLine();
            if (line == null) {
                System.out.println("Input non valido. Riprova.");
                continue;
            }
            line = line.trim();
            if (line.isEmpty()) {
                System.out.println("La scelta non può essere vuota. Inserisci 1 o 2.");
                continue;
            }
            try {
                choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= 2) {
                    break;
                }
                System.out.println("Opzione non valida. Inserisci 1 o 2.");
            } catch (NumberFormatException e) {
                System.out.println("Devi inserire un numero (1 o 2).");
            }
        }
        return choice;
    }
    public static void printError(Exception e){
        System.out.println(e.getMessage());
    }
}
