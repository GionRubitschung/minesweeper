import Models.Spielfeld;
import Services.UserInterface;

public class Minesweeper {
    private Spielfeld spielfeld;
    private UserInterface userInterface;

    public static void main(String[] args) {
        // In dieser Methode startet die Applikation
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.spielfeld = new Spielfeld();
        minesweeper.userInterface = new UserInterface(minesweeper.spielfeld);
        // Spiel wird über die Methode spieleSpiel in userInterface gestartet
        minesweeper.userInterface.spieleSpiel();
    }
}