package Services;

public class Validator {
    private boolean istGueltig = true;

    // Prüfung ob die Eingabe valid/ korrekt ist und gibt true oder false zurück
    public boolean validate(String eingabe){
        String[] eingabeTeile = eingabe.split(" ");
        if (eingabeTeile.length != 3) istGueltig = false;
        if (!eingabeTeile[0].equals("T") && !eingabeTeile[0].equals("M")) istGueltig = false;
        try {
            Integer.valueOf(eingabeTeile[1]);
            Integer.valueOf(eingabeTeile[2]);
        } catch (NumberFormatException e) {
            istGueltig = false;
        }
        return istGueltig; //Boolean intGueltig wird zurückgegeben
    }
}
