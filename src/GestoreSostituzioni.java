import java.io.PrintStream;
import java.util.ArrayList;

public class GestoreSostituzioni {
    private ArrayList<Lezione> lezioni;

    public GestoreSostituzioni(ArrayList<Lezione> lezioni) {
        this.lezioni = lezioni;
    }

    public void stampaSostitutoCompresenza(String docenteAssente, String giorno) {
        for (Lezione l : this.lezioni) {
            if (!l.getGiorno().equalsIgnoreCase(giorno)) continue;

            String[] docentiLezione = l.getCognome();
            boolean assentePresente = false;

            for (String d : docentiLezione) {
                if (d.trim().equalsIgnoreCase(docenteAssente)) {
                    assentePresente = true;
                    break;
                }
            }

            if (!assentePresente) continue;

            // Se c’è compresenza
            if (l.getCodocenza() == 'S') {
                for (String collega : docentiLezione) {
                    if (!collega.trim().equalsIgnoreCase(docenteAssente)) {
                        System.out.println(
                                "Lezione n° " + l.getNumero() +
                                        ", Durata: " + l.getDurata() +
                                        ", Materia: " + l.getMateria() +
                                        ", Docente assente: " + docenteAssente +
                                        ", Sostituto: " + collega.trim()
                        );
                    }
                }
            } else {
                // Nessuna compresenza per questa lezione
                System.out.println(
                        "Lezione n° " + l.getNumero() +
                                ", Docente assente: " + docenteAssente +
                                " -> no codocenza"
                );
            }
        }
    }
}
