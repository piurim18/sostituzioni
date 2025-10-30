//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GestoreSostituzioni {
    private ArrayList<Lezione> lezioni;

    public GestoreSostituzioni(ArrayList<Lezione> lezioni) {
        this.lezioni = lezioni;
    }

    public ArrayList<String[]> getSostituzioniComplete(String docenteAssente, String giorno) {
        ArrayList<String[]> risultati = new ArrayList();

        for(Lezione l : this.lezioni) {
            if (l.getGiorno().equalsIgnoreCase(giorno) && this.isDocenteInLezione(docenteAssente, l)) {
                String risultatoSostituzione = null;
                if (l.getCodocenza() == 'S') {
                    String collega = this.trovaCollegaCodocenza(docenteAssente, l);
                    if (collega != null) {
                        risultatoSostituzione = "Codocenza con " + collega;
                    }
                }

                if (risultatoSostituzione == null) {
                    if (this.isClasseQuinta(l.getClasse())) {
                        risultatoSostituzione = this.cercaSostitutoQuinte(docenteAssente, l);
                    } else {
                        risultatoSostituzione = this.cercaSostitutoNonQuinte(docenteAssente, l);
                    }
                }

                risultati.add(new String[]{l.getOrarioInizio(), docenteAssente + " - " + l.getMateria() + " → " + risultatoSostituzione});
            }
        }

        return risultati;
    }

    private String cercaSostitutoNonQuinte(String docenteAssente, Lezione lezione) {
        ArrayList<String> liberi = this.getDocentiLiberi(lezione.getOrarioInizio(), lezione.getGiorno());
        String classe = lezione.getClasse();
        String materia = lezione.getMateria();
        String sostituto = this.cercaDocenteStessaClasse(liberi, classe);
        if (sostituto != null) {
            return "Sostituito da " + sostituto + " (stessa classe)";
        } else {
            sostituto = this.cercaDocenteStessaMateria(liberi, materia);
            if (sostituto != null) {
                return "Sostituito da " + sostituto + " (materia affine)";
            } else {
                sostituto = this.cercaDocenteNonQuinta(liberi);
                return sostituto != null ? "Sostituito da " + sostituto + " (docente non quinta)" : this.cercaConOreLiberePagamento(docenteAssente, lezione);
            }
        }
    }

    private String cercaSostitutoQuinte(String docenteAssente, Lezione lezione) {
        ArrayList<String> liberi = this.getDocentiLiberi(lezione.getOrarioInizio(), lezione.getGiorno());
        String materia = lezione.getMateria();
        String sostituto = this.cercaDocenteStessaMateria(liberi, materia);
        if (sostituto != null) {
            return "Sostituito da " + sostituto + " (materia affine - quinta)";
        } else {
            return !liberi.isEmpty() ? "Sostituito da " + (String)liberi.get(0) + " (qualsiasi materia - quinta)" : this.cercaConOreLiberePagamento(docenteAssente, lezione);
        }
    }

    private String cercaConOreLiberePagamento(String docenteAssente, Lezione lezione) {
        String ora = lezione.getOrarioInizio();
        String giorno = lezione.getGiorno();
        ArrayList<String> liberi = this.getDocentiLiberi(ora, giorno);
        if (liberi.isEmpty()) {
            return "Nessun sostituto disponibile";
        } else {
            String sostitutoPrioritario = this.cercaDocenteConLezioneAdiacente(liberi, ora, giorno);
            return sostitutoPrioritario != null ? "Sostituito da " + sostitutoPrioritario + " (ore libere a pagamento - con lezione adiacente)" : "Sostituito da " + (String)liberi.get(0) + " (ore libere a pagamento)";
        }
    }

    private String cercaDocenteConLezioneAdiacente(ArrayList<String> liberi, String ora, String giorno) {
        String oraPrecedente = this.getOraPrecedente(ora);
        String oraSuccessiva = this.getOraSuccessiva(ora);

        for(String docente : liberi) {
            if (oraPrecedente != null && this.haLezioneInOra(docente, oraPrecedente, giorno)) {
                return docente;
            }

            if (oraSuccessiva != null && this.haLezioneInOra(docente, oraSuccessiva, giorno)) {
                return docente;
            }
        }

        return null;
    }

    private String cercaDocenteNonQuinta(ArrayList<String> liberi) {
        for(String docente : liberi) {
            if (!this.insegnaInQuinte(docente)) {
                return docente;
            }
        }

        return null;
    }

    private boolean insegnaInQuinte(String docente) {
        for(Lezione l : this.lezioni) {
            if (this.isDocenteInLezione(docente, l) && this.isClasseQuinta(l.getClasse())) {
                return true;
            }
        }

        return false;
    }

    private boolean isClasseQuinta(String classe) {
        return classe != null && (classe.toUpperCase().contains("5") || classe.toUpperCase().contains("QUINTA") || classe.toUpperCase().contains("V"));
    }

    private boolean haLezioneInOra(String docente, String ora, String giorno) {
        for(Lezione l : this.lezioni) {
            if (l.getGiorno().equalsIgnoreCase(giorno) && l.getOrarioInizio().equalsIgnoreCase(ora) && this.isDocenteInLezione(docente, l)) {
                return true;
            }
        }

        return false;
    }

    private String getOraPrecedente(String ora) {
        String[] ore = new String[]{"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};

        for(int i = 0; i < ore.length; ++i) {
            if (ore[i].equals(ora) && i > 0) {
                return ore[i - 1];
            }
        }

        return null;
    }

    private String getOraSuccessiva(String ora) {
        String[] ore = new String[]{"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};

        for(int i = 0; i < ore.length; ++i) {
            if (ore[i].equals(ora) && i < ore.length - 1) {
                return ore[i + 1];
            }
        }

        return null;
    }

    private boolean isDocenteInLezione(String docente, Lezione lezione) {
        return Arrays.stream(lezione.getCognome()).anyMatch((cognome) -> cognome.trim().equalsIgnoreCase(docente));
    }

    private String trovaCollegaCodocenza(String docenteAssente, Lezione lezione) {
        for(String collega : lezione.getCognome()) {
            if (!collega.trim().equalsIgnoreCase(docenteAssente)) {
                return collega.trim();
            }
        }

        return null;
    }

    public ArrayList<String> getDocentiLiberi(String ora, String giorno) {
        Set<String> tuttiDocenti = new HashSet();
        Set<String> impegnati = new HashSet();

        for (Lezione l : this.lezioni) {
//            for (String cognome : l.getCognome()) {
//                tuttiDocenti.add(cognome.trim());
//            }

            if (l.getGiorno().equalsIgnoreCase(giorno) && l.getOrarioInizio().equalsIgnoreCase(ora) && l.getMateria().equalsIgnoreCase("Disposizione") && l.getClasse().equalsIgnoreCase("Disposizione")) {
                for (String cognome : l.getCognome()) {
                    tuttiDocenti.add(cognome.trim());
                }
            }
        }

        ArrayList<String> liberi = new ArrayList(tuttiDocenti);
        //liberi.removeAll(impegnati);
        return liberi;
    }

    private String cercaDocenteStessaClasse(ArrayList<String> liberi, String classe) {
        for(Lezione lezione : this.lezioni) {
            if (lezione.getClasse().equalsIgnoreCase(classe)) {
                for(String cognome : lezione.getCognome()) {
                    String docente = cognome.trim();
                    if (liberi.contains(docente)) {
                        return docente;
                    }
                }
            }
        }

        return null;
    }

    private String cercaDocenteStessaMateria(ArrayList<String> liberi, String materia) {
        for(Lezione lezione : this.lezioni) {
            if (lezione.getMateria().equalsIgnoreCase(materia)) {
                for(String cognome : lezione.getCognome()) {
                    String docente = cognome.trim();
                    if (liberi.contains(docente)) {
                        return docente;
                    }
                }
            }
        }

        return null;
    }

    public ArrayList<String[]> getSostitutiCompresenza(String docenteAssente, String giorno) {
        return this.getSostituzioniComplete(docenteAssente, giorno);
    }

    public ArrayList<String[]> getSostituzioniConPriorita(String docenteAssente, String giorno) {
        return this.getSostituzioniComplete(docenteAssente, giorno);
    }
}
