package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.model.domain.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegisterView {
    private static final String[] KNOWN_EMAIL_DOMAINS = {
        "gmail.com", "hotmail.com", "outlook.com", "yahoo.com", "libero.it", "icloud.com",
            "live.com", "virgilio.it", "students.uniroma2.eu"
    };

    public static Credentials register() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\uD83D\uDC64 Inserisci un username: ");
        String username = reader.readLine();
        System.out.print("\uD83D\uDD11 Scegli una password: ");
        String password = reader.readLine();

        return new Credentials(username, password, Role.UTENTE);
    }

    public static User insertInfo() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = readLettersOnly(reader, "Nome: ");
        if (name == null) { System.out.println("Operazione annullata."); return null; }
        String surname = readLettersOnly(reader, "Cognome: ");
        if (surname == null) { System.out.println("Operazione annullata."); return null; }
        System.out.print("Data di nascita (yyyy/mm/dd): ");
        java.sql.Date dateOfBirth = getDate();
        String residentialAddress = readStreetAddressRequired(reader, "Indirizzo di residenza");
        String billingAddress = readStreetAddressOptional(reader, "Indirizzo di fatturazione");
        int i = 0;
        List<String> typeAddress = new ArrayList<>();
        List<String> address = new ArrayList<>();
        while (true) {
            System.out.println("'telefono' (xx xxxxxx)\t 'cellulare' (xxx-xxx-xxxx) 'email' (example@gmail.com))");
            String tipo;
            while (true) {
                System.out.print("Tipo recapito: ");
                tipo = reader.readLine();
                if (tipo != null) tipo = tipo.trim().toLowerCase();
                if (tipo != null && !tipo.isEmpty() &&
                        (tipo.equals("telefono") || tipo.equals("cellulare") || tipo.equals("email"))) {
                    break;
                }
                System.out.println("Valore non valido. Opzioni: telefono / cellulare / email");
            }
            typeAddress.add(i, tipo);

            String recapito;
            while (true) {
                System.out.print("Recapito: ");
                recapito = reader.readLine();
                if (recapito != null) recapito = recapito.trim();
                if (recapito == null || recapito.isEmpty()) {
                    System.out.println("Il recapito non può essere vuoto. Riprova.");
                    continue;
                }
                boolean ok = true;
                if (tipo.equals("email")) {
                    // Verifica formato email
                    if (!recapito.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        System.out.println("Formato email non valido. ");
                        ok = false;
                    } else {
                        // Controlla dominio noto
                        String[] parts = recapito.split("@");
                        String domain = parts.length == 2 ? parts[1].toLowerCase() : "";
                        boolean domainOk = false;
                        for (String allowed : KNOWN_EMAIL_DOMAINS) {
                            if (domain.equals(allowed)) {
                                domainOk = true;
                                break;
                            }
                        }
                        if (!domainOk) {
                            System.out.print("Dominio email non supportato. Usa uno tra: ");
                            for (int d = 0; d < KNOWN_EMAIL_DOMAINS.length; d++) {
                                System.out.print(KNOWN_EMAIL_DOMAINS[d]);
                                if (d < KNOWN_EMAIL_DOMAINS.length - 1) System.out.print(", ");
                            }
                            System.out.println(".");
                            ok = false;
                        }
                    }
                } else if (tipo.equals("cellulare") && !recapito.matches("^\\d{3}-\\d{3}-\\d{4}$")) {
                    System.out.println("Formato cellulare non valido. Usa es. 333-123-4567");
                    ok = false;
                } else if (tipo.equals("telefono") && !recapito.matches("^[0-9]{2} ?[0-9]{6}$")) {
                    System.out.println("Formato telefono non valido. Usa es. 06 123456");
                    ok = false;
                }
                if (ok) break;
            }
            address.add(i, recapito);
            System.out.print("Vuoi indicare questo recapito come preferito? (y/n): ");
            String response = reader.readLine();
            i++;
            if (response.equals("y")) {
                if (i == 1) {
                    return new User(name, surname, dateOfBirth, residentialAddress, billingAddress, TypeContact.valueOf(typeAddress.get(0)), address.get(0));
                } else {
                    User user = new User(name, surname, dateOfBirth, residentialAddress, billingAddress, TypeContact.valueOf(typeAddress.get(i - 1)), address.get(i - 1));
                    List<ContactNotPreferred> contactNotPreferred = new ArrayList<>();
                    for (int k = 0; k < i - 1; k++) {
                        contactNotPreferred.add(k, new ContactNotPreferred(TypeContact.valueOf(typeAddress.get(k)), address.get(k)));
                    }
                    user.setContactNotPreferred(contactNotPreferred);
                    return user;
                }
            } else if (!response.equals("n")) {
                System.out.println("\nRiprova");
            }
        }
    }

    private static java.sql.Date getDate() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Date date;
        java.sql.Date sqlBirthDate;
        String input;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);

        while (true) {
            try {
                input = reader.readLine();
                if (input.isEmpty()) {
                    System.out.print("La data non può essere vuota. Riprova: ");
                    continue;
                }
                dateFormat.setLenient(false);
                date = dateFormat.parse(input);

                if (date.after(new Date())) {
                    System.out.print("La data non può essere nel futuro. Riprova: ");
                    continue;
                }
                java.util.Calendar today = java.util.Calendar.getInstance();
                java.util.Calendar dob = java.util.Calendar.getInstance();
                dob.setTime(date);
                int age = today.get(java.util.Calendar.YEAR) - dob.get(java.util.Calendar.YEAR);
                if (today.get(java.util.Calendar.DAY_OF_YEAR) < dob.get(java.util.Calendar.DAY_OF_YEAR)) {
                    age--;
                }
                if (age < 18) {
                    System.out.print("Devi avere almeno 18 anni per registrarti. Inserisci una data valida: ");
                    continue;
                }

                sqlBirthDate = new java.sql.Date(date.getTime());
                break;
            } catch (ParseException e) {
                System.out.print("Formato non valido. Usa dd/MM/yyyy: ");
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return sqlBirthDate;
    }
    private static final Pattern STREET_ADDR = Pattern.compile("(?i)^(via|piazza)\\s+([A-Za-zÀ-ÖØ-öø-ÿ][A-Za-zÀ-ÖØ-öø-ÿ .'-]{1,})\\s+(\\d+[A-Za-z]?)$");

    private static String normalizeSpaces(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", " ");
    }

    private static String capitalizeWord(String w) {
        if (w.isEmpty()) return w;
        if (w.contains("'")) {
            String[] parts = w.split("'", -1);
            for (int i=0; i<parts.length; i++) {
                parts[i] = capitalizeWord(parts[i]);
            }
            return String.join("'", parts);
        }
        if (w.contains("-")) {
            String[] parts = w.split("-", -1);
            for (int i=0; i<parts.length; i++) {
                parts[i] = capitalizeWord(parts[i]);
            }
            return String.join("-", parts);
        }
        if (w.length() == 1) return w.substring(0,1).toUpperCase();
        return w.substring(0,1).toUpperCase() + w.substring(1).toLowerCase();
    }

    private static String capitalizeWords(String s) {
        String[] tokens = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<tokens.length; i++) {
            if (i>0) sb.append(' ');
            String t = tokens[i];
            String lower = t.toLowerCase();
            if (i>0 && (lower.equals("di") || lower.equals("del") || lower.equals("della") || lower.equals("dei") || lower.equals("delle"))) {
                sb.append(lower);
            } else {
                sb.append(capitalizeWord(t));
            }
        }
        return sb.toString();
    }

    private static String readStreetAddressRequired(BufferedReader reader, String label) throws IOException {
        while (true) {
            System.out.print(label + " (es. 'Via Roma 10'): ");
            String s = reader.readLine();
            if (s == null) continue;
            s = normalizeSpaces(s);
            Matcher m = STREET_ADDR.matcher(s);
            if (m.matches()) {
                String tipo = m.group(1).substring(0,1).toUpperCase() + m.group(1).substring(1).toLowerCase(); // Via/Piazza
                String nome = capitalizeWords(m.group(2));
                String civico = m.group(3).toUpperCase();
                return tipo + " " + nome + " " + civico;
            }
            System.out.println("Formato non valido. Usa: 'Via/Piazza + nomeVia + civico' (es. 'Via Roma 10').");
        }
    }

    private static String readStreetAddressOptional(BufferedReader reader, String label) throws IOException {
        while (true) {
            System.out.print(label + " (opzionale, es. 'Via Milano 25'): ");
            String s = reader.readLine();
            if (s == null) continue;
            s = normalizeSpaces(s);
            if (s.isEmpty()) return "";
            Matcher m = STREET_ADDR.matcher(s);
            if (m.matches()) {
                String tipo = m.group(1).substring(0,1).toUpperCase() + m.group(1).substring(1).toLowerCase();
                String nome = capitalizeWords(m.group(2));
                String civico = m.group(3).toUpperCase();
                return tipo + " " + nome + " " + civico;
            }
            System.out.println("Formato non valido. Usa: 'Via/Piazza + nomeVia + civico' (es. 'Piazza Duomo 1').");
        }
    }

    private static final Pattern ONLY_LETTERS_SPACES_APOS_HYPHEN =
            Pattern.compile("^[\\p{L}]+(?:[\\p{L}\\s'-]*[\\p{L}])?$");

    private static String readLettersOnly(BufferedReader reader, String prompt) throws IOException {
        while (true) {
            System.out.print(prompt);
            String s = reader.readLine();
            if (s == null) return null;
            s = s.trim().replaceAll("\\s+", " ");       // comprime spazi multipli

            if (ONLY_LETTERS_SPACES_APOS_HYPHEN.matcher(s).matches()) {
                return toTitleCase(s);
            }

            System.out.println("Valore non valido: usa solo lettere (eventuali spazi tra parole).");
        }
    }

    private static String toTitleCase(String s) {
        String[] parts = s.toLowerCase().split(" ");
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) continue;
            String p = parts[i];
            out.append(Character.toUpperCase(p.charAt(0)));
            if (p.length() > 1) out.append(p.substring(1));
            if (i < parts.length - 1) out.append(" ");
        }
        return out.toString();
    }
}
