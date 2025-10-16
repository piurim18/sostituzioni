import java.util.ArrayList;

public class Docente {
    private String cognome;
    private ArrayList<Lezione> lezioni = new ArrayList<>();

    public Docente(String cognome) {
        this.cognome = cognome;
    }

    public String getCognome() {
        return cognome;
    }

    public ArrayList<Lezione> getLezioni() {
        return lezioni;
    }

    public void aggiungiLezione(Lezione lezione) {
        lezioni.add(lezione);
    }

    public void stampaLezioni() {
        System.out.println("Lezioni del docente " + cognome + ":");
        for (Lezione lezione : lezioni) {
            System.out.println(lezione);
        }
    }

    @Override
    public String toString() {
        return "Docente{" +
                "cognome='" + cognome + '\'' +
                ", lezioni=" + lezioni +
                '}';
    }
}
