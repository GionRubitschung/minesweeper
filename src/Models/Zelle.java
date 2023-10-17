package Models;

public class Zelle {
    private int x;
    private int y;
    private String typ;
    private String zustand;
    private String icon;

    public Zelle(int x, int y, String typ, String zustand, String icon){
        // Variablen werden zugewiesen
        this.x = x;
        this.y = y;
        this.typ = typ;
        this.zustand = zustand;
        this.icon = icon;
    }

    // Zustand bekommen, gibt einen String zurück
    public String getZustand() {
        return zustand;
    }

    // Typ bekommen, gibt einen String zurück
    public String getTyp() {
        return typ;
    }
    // Position bekommen, gibt einen Integer zurück
    public int[] getPosition(){
        return new int[]{x, y};
    }
    // Icon bekommen, gibt Icon zurück, welches ein String ist
    public String getIcon() {
        if(zustand.equals("unendeckt")) return " ";
        if(zustand.equals("markiert")) return "!";
        return icon;
    }
    // Verdecktes Icon bekommen, gibt Icon zurück, welches ein String ist
    public String getIconVerdeckt(){
        return icon;
    }
    // Der Neue Zustand bekommen, gibt zustand (String) zurück
    public void neuerZustand(String zustand){
        this.zustand = zustand;
    }
}
