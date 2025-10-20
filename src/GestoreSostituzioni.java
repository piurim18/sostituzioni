import java.util.*;

public class GestoreSostituzioni {

    private ArrayList<Lezione> lezioni;
    ArrayList<ClasseCell> classeCell;
    ArrayList<DocenteCella> docenteCella;

    // Costruttore che prende la lista delle lezioni
    public GestoreSostituzioni(ArrayList<Lezione> lezioni) {
        this.lezioni = lezioni;
    }

    public void stampaSostitutoCompresenza(String docenteAssente) {
        for (Lezione l : lezioni) {
            String[] docentiLezione = l.getCognome();
            if (l.getCodocenza() == 'S') {  // verifica se c'è compresenza
                boolean assentePresente = false;
                for (String d : docentiLezione) {
                    if (d.trim().equalsIgnoreCase(docenteAssente)) {
                        assentePresente = true;
                        break;
                    }
                }

                if (assentePresente) {
                    // Trova il collega presente (diverso dall'assente)
                    for (String collega : docentiLezione) {
                        if (!collega.trim().equalsIgnoreCase(docenteAssente)) {
                            System.out.println("Lezione n° " + l.getNumero() +
                                    ", Durata: " + l.getDurata() +
                                    ", Materia: " + l.getMateria() +
                                    ", Docente assente: " + docenteAssente +
                                    ", Sostituto: " + collega.trim());
                        }
                    }
                }
            }
        }
    }

}
