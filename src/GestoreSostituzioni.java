import java.io.PrintStream;
import java.util.ArrayList;

public class GestoreSostituzioni {
    private ArrayList<Lezione> lezioni;
    ArrayList<ClasseCell> classeCell;
    ArrayList<DocenteCella> docenteCella;

    public GestoreSostituzioni(ArrayList<Lezione> lezioni) {
        this.lezioni = lezioni;
    }

    public void stampaSostitutoCompresenza(String docenteAssente, String giorno) {
        for(Lezione l : this.lezioni) {
            String[] docentiLezione = l.getCognome();
            if (l.getCodocenza() == 'S' && l.getGiorno().equalsIgnoreCase(giorno)) {
                boolean assentePresente = false;

                for(String d : docentiLezione) {
                    if (d.trim().equalsIgnoreCase(docenteAssente)) {
                        assentePresente = true;
                        break;
                    }
                }

                if (assentePresente) {
                    for(String collega : docentiLezione) {
                        if (!collega.trim().equalsIgnoreCase(docenteAssente)) {
                            PrintStream var10000 = System.out;
                            int var10001 = l.getNumero();
                            var10000.println("Lezione n° " + var10001 + ", Durata: " + l.getDurata() + ", Materia: " + l.getMateria() + ", Docente assente: " + docenteAssente + ", Sostituto: " + collega.trim());
                        }
                    }
                }
            }
        }

    }
}