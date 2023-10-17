package Services;

import Models.Spielfeld;

import java.util.Scanner;

public class UserInterface extends GameInformations {
    private Spielfeld spielfeld;
    private Validator validator;
    private Scanner console;

    public UserInterface(Spielfeld spielfeld){
        this.spielfeld = spielfeld;
        console = new Scanner(System.in);
        validator = new Validator();
    }

    // spieleSpiel wird direkt von der Main Methode aus aufgerufen
    public void spieleSpiel(){
        displayRules(); // Regeln werden in die Konsole geschrieben.
        System.out.println(spielfeld.getFinalesSpielfeldString());
        boolean amSpielen = true;

        do {
            var input = readInput();
            var result = spielfeld.macheZellenAktion(Integer.parseInt(input[2]) - 1, Integer.parseInt(input[1]) - 1, input[0]);
            switch (result){
                case "bombe": // Spiel verloren, Spiel ist fertig
                    displayLost();
                    System.out.println(spielfeld.getFinalesSpielfeldString());
                    amSpielen = false;
                    break;
                case "aktioniert":
                    displayAlready();
                    break;
                case "gewonnen": // Spiel gewonnen, Spiel ist fertig
                    displayWin();
                    amSpielen = false;
                    break;
                default:
                    System.out.println(spielfeld.getSpielfeldString());
                    break;
            }
        }while (amSpielen);
    }

    // Geschriebenes wird eingelesen
    private String[] readInput(){
        System.out.print("Ihre Eingabe: ");
        String eingabe = console.nextLine();
        var result = validator.validate(eingabe); // validate Methode wird aufgerufen
        if(result){
            return eingabe.split(" ");
        } else{
            return new String[]{stringError()};
        }
    }
}
