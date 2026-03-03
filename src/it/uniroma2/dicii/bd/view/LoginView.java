package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.model.domain.Credentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginView {
    /**
     * Legge username e password in modo robusto (niente stringhe vuote o soli spazi).
     * NON verifica contro il DB: restituisce semplicemente le credenziali inserite.
     */
    public static Credentials authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String username;
        String password;

        // Username: non vuoto
        while (true) {
            System.out.print("\uD83D\uDC64 Username: ");
            username = reader.readLine();
            if (username != null) username = username.trim();
            if (username != null && !username.isEmpty()) break;
            System.out.println("Username non può essere vuoto. Riprova.");
        }

        // Password: non vuota
        while (true) {
            System.out.print("\uD83D\uDD11 Password: ");
            password = reader.readLine();
            if (password != null) password = password.trim();
            if (password != null && !password.isEmpty()) break;
            System.out.println("Password non può essere vuota. Riprova.");
        }

        // Il terzo parametro (ruolo) resta null: verrà impostato dal livello applicativo dopo la verifica
        return new Credentials(username, password, null);
    }

    /**
     * Da chiamare dal controller quando il login fallisce (credenziali non trovate/errate).
     * Ritorna true se l'utente vuole riprovare, false per tornare al menu.
     */
    public static boolean askRetryLogin() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Credenziali non valide. Vuoi riprovare? (y/n): ");
            String s = reader.readLine();
            if (s == null) continue;
            s = s.trim().toLowerCase();
            if (s.equals("y") || s.equals("s") || s.equals("si")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Rispondi con 'y'/'n'.");
        }
    }
}