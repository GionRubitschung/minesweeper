package Services;

import Models.Zelle;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SpielfeldManager {
    protected Zelle[][] spielfeld;
    protected Zelle[][] offenesSpielfeld;

    private Random random = new Random();

    protected void generiereSpielfeld(){

        initialisiereZellen(); // Zellen müssen initialisiert werden, da ansonsten eine NullPointerException geworfen wird
        generiereBomben(); // Die Bomben müssen zuerst generiert werden, da die nachfolgenden Felder die Bomben im Umkreis finden und abzählen müssen

        // Durch das Spielfeld interieren => unentdeckt
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!spielfeld[i][j].getTyp().equals("Bombe")){ // Falls die Zelle keine Bombe ist kann das Icon ermittelt werden
                    var icon = entdeckeBomben(i, j); // Das Icon ist in diesem Fall eine Zahl, da die Zelle herausfinden muss, wie viele Bomben in ihrem Umkreis sind --> Entsprechende Zahl ist dann das Icon
                    spielfeld[i][j] = new Zelle(i, j, "Leer", "unendeckt", String.valueOf(icon));
                }
            }
        }
        // Iteration durchs Spielfeld => entdeckt
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                offenesSpielfeld[i][j] = new Zelle(i, j, "offen", "entdeckt", spielfeld[i][j].getIconVerdeckt());
            }
        }
    }

    private void initialisiereZellen(){
        // Zellen werden initialisiert
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                spielfeld[i][j] = new Zelle(i, j, "Null", "Null", "N");
                offenesSpielfeld[i][j] = new Zelle(i, j, "Null", "Null", "N");
            }
        }
    }

    private void generiereBomben(){
        // Es müssen 10 Bomben verteilt werden, welche in dieser Methode generiert werden
        for(int i = 0; i < 10; i++){
            int randX = random.nextInt(8);
            int randY = random.nextInt(8);
            spielfeld[randX][randY] = new Zelle(randX, randY, "Bombe", "unendeckt", "*");
        }
    }

    private int entdeckeBomben(int x, int y){
        // x und y Achsen der anliegenden Zellen der Bombe. mX steht für minusX, pX für plus X, y Achsen analog
        int mX = x - 1;
        int pX = x + 1;
        int mY = y - 1;
        int pY = y + 1;

        /*
            Bei den folgenden if Statements muss vorher überprüft werden ob eine Achse ausserhalb des Spielfeldes ist.
            Wenn also eine Achse nicht in diesem Bereich liegt ist das statement false und es werden nicht zu viele Bomben berechnet.
        */
        int anzahlBomben = 0;
        if(!(mX < 0) && spielfeld[mX][y].getTyp().equals("Bombe")) anzahlBomben++;
        if(!(pX > 7) && spielfeld[pX][y].getTyp().equals("Bombe")) anzahlBomben++;
        if(!(mY < 0) && spielfeld[x][mY].getTyp().equals("Bombe")) anzahlBomben++;
        if(!(pY > 7) && spielfeld[x][pY].getTyp().equals("Bombe")) anzahlBomben++;
        if(!(mX < 0) && !(mY < 0) && spielfeld[mX][mY].getTyp().equals("Bombe")) anzahlBomben++;
        if(!(mX < 0) && !(pY > 7) && spielfeld[mX][pY].getTyp().equals("Bombe")) anzahlBomben++;
        if(!(pX > 7) && !(mY < 0) && spielfeld[pX][mY].getTyp().equals("Bombe")) anzahlBomben++;
        if(!(pX > 7) && !(pY > 7) && spielfeld[pX][pY].getTyp().equals("Bombe")) anzahlBomben++;
        return anzahlBomben;
    }

    public String macheZellenAktion(int x, int y, String aktion) {
        // String wird zurückgegeben, prüft welche Aktion auf welches Feld eingegeben wurde
        if(checkeErsteAktion()){
            boolean checkeIcon = true;
            do {
                if(!spielfeld[x][y].getIconVerdeckt().equals("0")) generiereSpielfeld();
                else checkeIcon = false;
            }while (checkeIcon);
        }

        if(spielfeld[x][y].getZustand().equals("entdeckt") || spielfeld[x][y].getZustand().equals("markiert")){
            return "aktioniert"; // String "aktioniert" wird zurückgeben
        }
        if(aktion.equals("M")){
            spielfeld[x][y].neuerZustand("markiert"); // String "markiert" wird zurückgeben
        }
        else if(aktion.equals("T")){
            // Aktion "T" ist für Testen bzw. aufdecken eines Felds
            spielfeld[x][y].neuerZustand("entdeckt");
            if(spielfeld[x][y].getIconVerdeckt().equals("*")) return "bombe";
            if(spielfeld[x][y].getIconVerdeckt().matches("-?\\d+") && (Integer.parseInt(spielfeld[x][y].getIconVerdeckt()) == 0)){
                deckeRandZahlenAuf(x, y);
                deckeUmliegendeFelderAuf(x, y);
                checkeAuf0AmRand();
            }
        }
        if(checkObGewonnen()) return "gewonnen"; // String "gewonnen" wird zurückgegeben
        return "erfolgreich"; // String "erfolgreich" wird zurückgegeben
    }

    private void deckeUmliegendeFelderAuf(int x, int y) {
        /*
            Diese Methode wird alle Umliegenden Felder mit einer Null und dessen Grenze (0,1,2 etc.) aufdenken.
            Dazu wird vom Startpunkt zuerst richtung minus Y durch itiriert, dadruch erhält man die "tiefste" Y Koordinate. Danach richtung plus Y wo die "höchste" Y Koordinate bestimmt wird.
            Dies geschieht auch Analog auf der X-Achse. Sobald diese Punkte bestimmt wurden können die Ränder herausgefunden werden.
         */
        // Äusserste Y Koordinaten
        int minY = findeRandY(x, y, -1);
        int maxY = findeRandY(x, y, 1);

        for(int i = minY; i <= maxY; i++){
            deckeXAchseAuf(x, i);
        }
    }

    private int findeRandY(int x, int y, int operant){
        // Findet ein random X und gibt es zurück
        boolean foundY = false;
        do {
            y += operant;
            if(y >= 0 && y <= 7 && spielfeld[x][y].getIconVerdeckt().matches("-?\\d+")){
                int value = Integer.parseInt(spielfeld[x][y].getIconVerdeckt());
                spielfeld[x][y].neuerZustand("entdeckt");
                deckeRandZahlenAuf(x, y);
                if (value > 0 || y == 7 || y == 0) {
                    foundY = true;
                }
            }
            if (y < 0 || y > 7) foundY = true;
        }while(!foundY);
        if(operant == -1 && y != 0 &&  y + 1 <= 7) return y + 1;
        if(operant == 1 && y != 7 &&  y - 1 >= 0) return y - 1;
        return y;
    }

    private int findeRandX(int x, int y, int operant){
        // Findet ein random X und gibt es zurück
        boolean foundX = false;
        do {
            x += operant;
            if(x >= 0 && x <= 7 && spielfeld[x][y].getIconVerdeckt().matches("-?\\d+")){
                int value = Integer.parseInt(spielfeld[x][y].getIconVerdeckt());
                spielfeld[x][y].neuerZustand("entdeckt");
                deckeRandZahlenAuf(x, y);
                if (value > 0 || x == 7 || x == 0) {
                    foundX = true;
                }
            }
            if (x < 0 || x > 7) foundX = true;
        }while(!foundX);
        if(operant == -1 && x != 0 && x + 1 <= 7) return x + 1;
        if(operant == 1 && x != 7 && x - 1 >= 0) return x - 1;
        return x;
    }

    private void deckeXAchseAuf(int x, int y){
        // Deckt X Achse Auf
        int maxX = findeRandX(x, y, 1);
        int minX = findeRandX(x, y, -1);

        for(int i = minX; i <= maxX; i++){
            int maxY = findeRandY(i, y, 1);
            int minY = findeRandY(i, y, -1);
            for(int j = minY; j <= maxY; j++){
                findeRandX(i, j, 1);
                findeRandX(i, j, -1);
            }
        }
    }

    private void deckeRandZahlenAuf(int x, int y){
        if(Integer.parseInt(spielfeld[x][y].getIconVerdeckt()) == 0){
            // x und y Achsen der Anliegenden Zellen der Bombe. mX steht für minusX, pX für plus X, y Achsen analog
            int mX = x - 1;
            int pX = x + 1;
            int mY = y - 1;
            int pY = y + 1;

        /*
            Bei den folgenden if Statements muss vorher überprüft werden ob eine Achse ausserhalb des Spielfeldes ist.
            Wenn also eine Achse nicht in diesem Bereich liegt ist das statement false und es werden nicht zu viele Bomben berechnet.
        */
            if(!(mX < 0)) spielfeld[mX][y].neuerZustand("entdeckt");
            if(!(pX > 7)) spielfeld[pX][y].neuerZustand("entdeckt");
            if(!(mY < 0)) spielfeld[x][mY].neuerZustand("entdeckt");
            if(!(pY > 7)) spielfeld[x][pY].neuerZustand("entdeckt");
            if(!(mX < 0) && !(mY < 0)) spielfeld[mX][mY].neuerZustand("entdeckt");
            if(!(mX < 0) && !(pY > 7)) spielfeld[mX][pY].neuerZustand("entdeckt");
            if(!(pX > 7) && !(mY < 0)) spielfeld[pX][mY].neuerZustand("entdeckt");
            if(!(pX > 7) && !(pY > 7)) spielfeld[pX][pY].neuerZustand("entdeckt");
        }
    }

    private void checkeAuf0AmRand(){
        // Prüft Felder bzw. die 0 am Rand
        List<Zelle> pruefendeFelder = Arrays.stream(spielfeld)
                .flatMap(Arrays::stream)
                .filter(zelle -> zelle.getZustand().equals("entdeckt") && zelle.getIcon().equals("0"))
                .collect(Collectors.toList());
        for (Zelle feld: pruefendeFelder) {
            deckeUmliegendeFelderAuf(feld.getPosition()[0], feld.getPosition()[1]);
        }
    }

    private boolean checkeErsteAktion(){
        // Erste Aktion
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!spielfeld[i][j].getIcon().equals(" ")) return false;
            }
        }
        return true;
    }

    private boolean checkObGewonnen(){
        // Prüft ob man gewinnt, um nachher den Wert "gewonnen" in der "macheZellenAktion" Methode zurückzugeben
        List<Zelle> bomben = Arrays.stream(spielfeld)
                .flatMap(Arrays::stream)
                .filter(zelle -> zelle.getTyp().equals("Bombe"))
                .collect(Collectors.toList());
        for (Zelle bombe :bomben) {
            if(!bombe.getZustand().equals("markiert") || bombe.getZustand().equals("unendeckt")) return false;
        }
        return true;
    }
}
