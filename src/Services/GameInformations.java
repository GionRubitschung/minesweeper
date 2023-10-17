package Services;

class GameInformations {
    // In dieser Klasse werden die Informationen zum Spiel in der Konsole ausgegeben

    void displayRules() {
        // Diese Methode erklärt die Regeln des Spiels
        System.out.println("                   <><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("                           Willkommen bei Minesweeper          ");
        System.out.println("                    Spiel von Gion Rubitschung und Ruben Nauer ");
        System.out.println("                   <><><><><><><><><><><><><><><><><><><><><><>" + "\n");
        System.out.println(" ----------------------------------Regeln:----------------------------------------");
        System.out.println("| Geben Sie zuerst M für MARKIEREN oder T für TESTEN bzw. AUFDECKEN ein.         |");
        System.out.println("| Anschliessend geben sie die Horizontale Zahl ein, danach die Vertikale.        |");
        System.out.println("| Die Eingabe soll beispielsweise folgendermassen aussehen: \"T 3 6\" ODER \"M 4 1\" |");
        System.out.println("| Um zu gewinnen müssen Sie alle Bombenfelder markiert haben                     |");
        System.out.println(" ---------------------------------------------------------------------------------");
    }

    String stringError() {
        // Wenn eine falsche bzw. unsaubere Eingabe gemacht wurde, wird diese Methode aufgerufen
        String error = (" ----------------------------------Error:----------------------------------------\n");
        error += ("| Unsaubere / Falsche Eingabe.                                                   |\n");
        error += ("| Die Eingabe soll beispielsweise folgendermassen aussehen: \"T 3 6\" ODER \"M 4 1\" |\n");
        error += ("| Versuchen Sie es nochmals.                                                      |\n");
        error += (" --------------------------------------------------------------------------------\n");
        return error;
    }

    void displayWin() {
        // Wird am Ende des Spiels aufgerufen (wenn alle Bomben aufgedeckt wurden), wenn der Spieler gewinnt
        System.out.println(" ----------------------------------Gewonnen!----------------------------------------");
        System.out.println("|*************Herzlichen Glückwunsch, Sie haben Minesweeper besiegt!****************|");
        System.out.println(" -----------------------------------------------------------------------------------");
    }

    void displayLost() {
        // Wird am Ende des Spiels aufgerufen (wenn der Spieler eine Bombe als Feld ausgewählt hat)
        System.out.println(" ---------------------------------Verloren:--------------------------------------");
        System.out.println("|                  ---_._Sie haben leider verloren_._---                         |");
        System.out.println(" --------------------------------------------------------------------------------");
    }

    void displayAlready() {
        // Wird aufgerufen wenn die ausgewählte Zelle bereits aufgedeckt oder markiert wurde.
        System.out.println(" ---------------------------------Ungültig:--------------------------------------");
        System.out.println("|      ---_._Diese Zelle wurde bereits aufgedeckt oder markiert_._---            |");
        System.out.println(" --------------------------------------------------------------------------------");
    }
}
