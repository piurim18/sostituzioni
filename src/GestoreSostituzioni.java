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
            }    else {

                String oraLezione = l.getOrarioInizio();
                String sostitutoTrovato = null;

                for (Lezione possibileSostituto : this.lezioni) {

                    if (!possibileSostituto.getGiorno().equalsIgnoreCase(giorno))
                        continue;

                    if (!possibileSostituto.getOrarioInizio().equalsIgnoreCase(oraLezione))
                        continue;


                    if (possibileSostituto.getMateria().equalsIgnoreCase("disposizione")) {

                        for (String disp : possibileSostituto.getCognome()) {
                            sostitutoTrovato = disp.trim();
                            break;
                        }
                        break;
                    }
                }

                if (sostitutoTrovato != null) {
                    System.out.println(
                            "Lezione n° " + l.getNumero() +
                                    ", Ora: " + oraLezione +
                                    ", Materia: " + l.getMateria() +
                                    ", Docente assente: " + docenteAssente +
                                    ", Sostituto (disposizione): " + sostitutoTrovato
                    );
                } else {
                    System.out.println(
                            "Lezione n° " + l.getNumero() +
                                    ", Ora: " + oraLezione +
                                    ", Materia: " + l.getMateria() +
                                    ", Docente assente: " + docenteAssente +
                                    " → Nessun docente in disposizione trovato"
                    );
                }
        }
    }
}
}
