package Models;

import Services.*;

public class Spielfeld extends SpielfeldManager {
    private int spielZuege;

    public Spielfeld(){
        // Spielfeld mit den Zellen
        spielfeld = new Zelle[8][8];
        offenesSpielfeld = new Zelle[8][8];
        generiereSpielfeld();
    }

    public Zelle[][] getSpielfeld() {
        return spielfeld;
    }

    // Spielfeld String bekommen und zurückgeben
    public String getSpielfeldString(){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                if(i == 0 & j == 0) output.append("     ");
                else if(i == 0) output.append(j).append("    ");
                else if(j == 0) output.append(i).append("    ");
                else{
                    var icon = spielfeld[j - 1][i - 1].getIcon();
                    output.append(icon).append("    ");
                }
            }
            if (i != 8){
                output.append("\n");
            }
        }
        return output.toString();
    }

    // Schlussendliches Spielfeld bekommen und zurückgeben
    public String getFinalesSpielfeldString(){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                if(i == 0 & j == 0) output.append("     ");
                else if(i == 0) output.append(j).append("    ");
                else if(j == 0) output.append(i).append("    ");
                else{
                    var icon = offenesSpielfeld[j - 1][i - 1].getIcon();
                    output.append(icon).append("    ");
                }
            }
            if (i != 8){
                output.append("\n");
            }
        }
        return output.toString();
    }
}
