import java.util.*;
import java.util.stream.Collectors;

public class GestoreSostituzioni {
    private ArrayList<Lezione> lezioni;
    private static final String DISPOSIZIONE = "Disposizione";
    private ArrayList<String> docentiAssenti;

    private static final String COLORE_CODOCENZA = "#FFB84D"; // ARAN
    private static final String COLORE_DISPOSIZIONE = "#66CDAA"; // verdino
    private static final String COLORE_DISPOSIZIONE_STESSA_CLASSE = "#4169E1"; // azzurro
    private static final String COLORE_MATERIA_AFFINE = "#D3D3D3"; // grigio
    private static final String COLORE_COMPRESENZA = "#BA68C8"; // viola
    private static final String COLORE_PAGAMENTO = "#F3E5F5"; // violino
    private static final String COLORE_NESSUNO = "#FFFFFF"; // bianco

    public GestoreSostituzioni(ArrayList<Lezione> lezioni) {
        this.lezioni = lezioni;
        this.docentiAssenti = new ArrayList<>();
    }

    public void setDocentiAssenti(ArrayList<String> docentiAssenti) {
        this.docentiAssenti = docentiAssenti != null ? new ArrayList<>(docentiAssenti) : new ArrayList<>();
        System.out.println("Docenti assenti impostati: " + this.docentiAssenti);
    }

    private boolean isDocenteAssente(String docente) {
        return docentiAssenti.contains(docente);
    }

    private ArrayList<String> filtraDocentiLiberi(ArrayList<String> liberi) {
        if (liberi == null) return new ArrayList<>();

        ArrayList<String> filtrati = new ArrayList<>();
        for (String docente : liberi) {
            if (!isDocenteAssente(docente)) {
                filtrati.add(docente);
            }
        }
        return filtrati;
    }

    public ArrayList<String[]> getSostituzioniComplete(String docenteAssente, String giorno) {
        ArrayList<String[]> risultatiPrimari = new ArrayList<>();
        ArrayList<Sostituzione> tutteSostituzioni = new ArrayList<>();

        String[] oreGiornata = {"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};

        for (String ora : oreGiornata) {
            List<Lezione> lezioniInOra = new ArrayList<>();
            for (Lezione l : this.lezioni) {
                if (l.getGiorno().equalsIgnoreCase(giorno) &&
                        this.isDocenteInLezione(docenteAssente, l) &&
                        this.getOreLezione(l).contains(ora)) {

                    if(DISPOSIZIONE.equalsIgnoreCase(l.getMateria())) {
                        continue;
                    }
                    lezioniInOra.add(l);
                }
            }

            if (!lezioniInOra.isEmpty()) {
                System.out.println("=== ORA " + ora + " ===");
                System.out.println("Lezioni del docente assente: " + lezioniInOra.size());

                boolean sostituzioneTrovata = false;

                // PRIORITÀ 1: CODOCENZA
                for (Lezione lezione : lezioniInOra) {
                    if (lezione.getCodocenza() == 'S') {
                        String collega = this.trovaCollegaCodocenza(docenteAssente, lezione);
                        if (collega != null && !isDocenteAssente(collega)) {
                            System.out.println("Trovata codocenza con: " + collega);
                            Sostituzione sost = new Sostituzione(ora, docenteAssente, lezione.getMateria(),
                                    lezione.getClasse(), "Sostituito da " + collega + " (codocenza)",
                                    collega, 1, COLORE_CODOCENZA);
                            tutteSostituzioni.add(sost);
                            sostituzioneTrovata = true;
                            break;
                        }
                    }
                }

                // PRIORITÀ 2: DISPOSIZIONI
                if (!sostituzioneTrovata) {
                    for (Lezione lezione : lezioniInOra) {
                        Sostituzione sostDisposizione = this.cercaConDisposizioniPerOra(docenteAssente, lezione, ora);
                        if (sostDisposizione != null && sostDisposizione.getSostituto() != null) {
                            System.out.println("Trovata disposizione con: " + sostDisposizione.getSostituto());
                            tutteSostituzioni.add(sostDisposizione);
                            sostituzioneTrovata = true;
                            break;
                        }
                    }
                }

                // PRIORITÀ 3: COMPRESENZE
                if (!sostituzioneTrovata) {
                    for (Lezione lezione : lezioniInOra) {
                        Sostituzione sost = this.trovaSostituzioneSenzaDisposizioni(docenteAssente, lezione, ora);
                        if (sost != null) {
                            System.out.println("Trovata compresenza con: " + sost.getSostituto());
                            tutteSostituzioni.add(sost);
                            sostituzioneTrovata = true;
                        }
                    }
                }

                if (!sostituzioneTrovata) {
                    System.out.println("Nessuna sostituzione trovata per l'ora " + ora);
                    // Aggiungi una sostituzione vuota per questa ora
                    tutteSostituzioni.add(new Sostituzione(ora, docenteAssente, "", "",
                            "Nessun sostituto disponibile", null, 6, COLORE_NESSUNO));
                }
            } else {
                System.out.println("Nessuna lezione del docente assente per l'ora " + ora);
            }
        }

        System.out.println("=== OTTIMIZZAZIONE ORIZZONTALE ===");
        System.out.println("Sostituzioni prima dell'ottimizzazione: " + tutteSostituzioni.size());

        // LOG DETTAGLIATO PRIMA DELL'OTTIMIZZAZIONE
        for(Sostituzione sost : tutteSostituzioni) {
            if (sost.getSostituto() != null) {
                System.out.println("PRIMA OTTIM: Ora " + sost.getOra() + " - " + sost.getDocenteAssente() +
                        " -> " + sost.getSostituto() + " (Pri: " + sost.getPriorita() + ")");
            } else {
                System.out.println("PRIMA OTTIM: Ora " + sost.getOra() + " - " + sost.getDocenteAssente() +
                        " -> NESSUNO (Pri: " + sost.getPriorita() + ")");
            }
        }

        List<Sostituzione> sostituzioniOttimizzate = this.ottimizzaSostituzioniOrizzontale(tutteSostituzioni, giorno);
        System.out.println("Sostituzioni dopo l'ottimizzazione: " + sostituzioniOttimizzate.size());

        // LOG DETTAGLIATO DOPO L'OTTIMIZZAZIONE
        for(Sostituzione sost : sostituzioniOttimizzate) {
            if (sost.getSostituto() != null) {
                System.out.println("DOPO OTTIM: Ora " + sost.getOra() + " - " + sost.getDocenteAssente() +
                        " -> " + sost.getSostituto() + " (Pri: " + sost.getPriorita() + ")");
            } else {
                System.out.println("DOPO OTTIM: Ora " + sost.getOra() + " - " + sost.getDocenteAssente() +
                        " -> NESSUNO (Pri: " + sost.getPriorita() + ")");
            }
        }

        for(Sostituzione sost : sostituzioniOttimizzate) {
            System.out.println("Ora " + sost.getOra() + ": " + sost.getRisultato());

            // STAMPA
            String sostitutoPerTabella;
            if (sost.getSostituto() != null) {
                sostitutoPerTabella = sost.getSostituto();
            } else {
                if (sost.getRisultato().contains("Codocenza")) {
                    sostitutoPerTabella = "Codocenza";
                } else if (sost.getRisultato().contains("Nessun sostituto")) {
                    sostitutoPerTabella = "-";
                } else {
                    sostitutoPerTabella = "-";
                }
            }

            risultatiPrimari.add(new String[]{
                    sost.getOra(),
                    sostitutoPerTabella,
                    sost.getColore()
            });
        }

        if (risultatiPrimari.isEmpty()) {
            for(String ora : new String[]{"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"}) {
                risultatiPrimari.add(new String[]{ora, "-", COLORE_NESSUNO});
            }
        } else {
            Set<String> orePresenti = new HashSet<>();
            for(String[] risultato : risultatiPrimari) {
                orePresenti.add(risultato[0]);
            }

            for(String ora : new String[]{"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"}) {
                if (!orePresenti.contains(ora)) {
                    risultatiPrimari.add(new String[]{ora, "-", COLORE_NESSUNO});
                }
            }

            risultatiPrimari.sort((a, b) -> {
                String[] oreOrdine = {"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};
                int indexA = Arrays.asList(oreOrdine).indexOf(a[0]);
                int indexB = Arrays.asList(oreOrdine).indexOf(b[0]);
                return Integer.compare(indexA, indexB);
            });
        }

        return risultatiPrimari;
    }

    private Sostituzione cercaConDisposizioniPerOra(String docenteAssente, Lezione lezione, String ora) {
        String giorno = lezione.getGiorno();
        String classe = lezione.getClasse();
        String materia = lezione.getMateria();

        ArrayList<String> liberi = this.getDocentiLiberi(ora, giorno);
        liberi = filtraDocentiLiberi(liberi);

        System.out.println("=== CERCA CON DISPOSIZIONI PER ORA " + ora + " ===");
        System.out.println("Classe: " + classe + ", Materia: " + materia + ", Docenti liberi (filtrati): " + liberi);

        // Docente della STESSA CLASSE
        String sostituto = this.cercaDocenteStessaClasse(liberi, classe);
        if (sostituto != null) {
            System.out.println("Trovato docente stessa classe: " + sostituto);
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + sostituto + " (stessa classe - disposizione)",
                    sostituto, 2, COLORE_DISPOSIZIONE_STESSA_CLASSE);
        }

        // Docente di MATERIA AFFINE
        sostituto = this.cercaDocenteStessaMateria(liberi, materia);
        if (sostituto != null) {
            System.out.println("Trovato docente materia affine: " + sostituto);
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + sostituto + " (materia affine - disposizione)",
                    sostituto, 2, COLORE_MATERIA_AFFINE);
        }

        // QUALSIASI docente libero in disposizione
        if (!liberi.isEmpty()) {
            System.out.println("Trovato docente qualsiasi: " + liberi.get(0));
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + liberi.get(0) + " (qualsiasi docente - disposizione)",
                    liberi.get(0), 2, COLORE_DISPOSIZIONE);
        }

        System.out.println("Nessun docente disponibile per disposizioni");
        return null;
    }

    private Sostituzione trovaSostituzioneSenzaDisposizioni(String docenteAssente, Lezione lezione, String ora) {
        if (this.isClasseQuinta(lezione.getClasse())) {
            System.out.println("Cerca sostituto per CLASSE QUINTA");
            return this.cercaSostitutoQuinteSenzaDisposizioni(docenteAssente, lezione, ora);
        } else {
            System.out.println("Cerca sostituto per CLASSE NON QUINTA");
            return this.cercaSostitutoNonQuinteSenzaDisposizioni(docenteAssente, lezione, ora);
        }
    }

    private Sostituzione cercaSostitutoNonQuinteSenzaDisposizioni(String docenteAssente, Lezione lezione, String ora) {
        String giorno = lezione.getGiorno();
        String classe = lezione.getClasse();
        String materia = lezione.getMateria();

        ArrayList<String> liberi = this.getDocentiCompletamenteLiberi(ora, giorno);
        liberi = filtraDocentiLiberi(liberi);

        System.out.println("Cerca sostituto NON quinte SENZA DISPOSIZIONI - Ora: " + ora + ", Classe: " + classe + ", Materia: " + materia + ", Docenti liberi (filtrati): " + liberi);

        // Docenti della CLASSE o MATERIA AFFINE
        String sostituto = this.cercaDocenteStessaClasse(liberi, classe);
        if (sostituto != null) {
            System.out.println("Trovato docente stessa classe (compresenza): " + sostituto);
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + sostituto + " (stessa classe - compresenza non quinta)",
                    sostituto, 3, COLORE_COMPRESENZA);
        }

        sostituto = this.cercaDocenteStessaMateria(liberi, materia);
        if (sostituto != null) {
            System.out.println("Trovato docente materia affine (compresenza): " + sostituto);
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + sostituto + " (materia affine - compresenza non quinta)",
                    sostituto, 3, COLORE_MATERIA_AFFINE);
        }

        // docenti NON delle QUINTE
        sostituto = this.cercaDocenteNonQuinta(liberi);
        if (sostituto != null) {
            System.out.println("Trovato docente non quinta: " + sostituto);
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + sostituto + " (docente non quinta - compresenza)",
                    sostituto, 3, COLORE_COMPRESENZA);
        }

        System.out.println("Nessun docente trovato per compresenza non quinte, cerca ore libere a pagamento");
        // ORE LIBERE A PAGAMENTO
        return this.cercaConOreLiberePagamento(docenteAssente, lezione);
    }

    private Sostituzione cercaSostitutoQuinteSenzaDisposizioni(String docenteAssente, Lezione lezione, String ora) {
        String giorno = lezione.getGiorno();
        String classe = lezione.getClasse();
        String materia = lezione.getMateria();

        ArrayList<String> liberi = this.getDocentiCompletamenteLiberi(ora, giorno);
        liberi = filtraDocentiLiberi(liberi);

        System.out.println("Cerca sostituto QUINTE SENZA DISPOSIZIONI - Ora: " + ora + ", Materia: " + materia + ", Docenti liberi (filtrati): " + liberi);

        // MATERIA AFFINE
        String sostituto = this.cercaDocenteStessaMateria(liberi, materia);
        if (sostituto != null) {
            System.out.println("Trovato docente materia affine (quinta): " + sostituto);
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + sostituto + " (materia affine - compresenza quinta)",
                    sostituto, 4, COLORE_MATERIA_AFFINE);
        }

        // QUALSIASI MATERIA
        if (!liberi.isEmpty()) {
            System.out.println("Trovato docente qualsiasi (quinta): " + liberi.get(0));
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + liberi.get(0) + " (qualsiasi materia - compresenza quinta)",
                    liberi.get(0), 4, COLORE_COMPRESENZA);
        }

        System.out.println("Nessun docente trovato per compresenza quinte, cerca ore libere a pagamento");
        // ORE LIBERE A PAGAMENTO
        return this.cercaConOreLiberePagamento(docenteAssente, lezione);
    }

    private Sostituzione cercaConOreLiberePagamento(String docenteAssente, Lezione lezione) {
        String ora = lezione.getOrarioInizio();
        String giorno = lezione.getGiorno();
        String classe = lezione.getClasse();
        String materia = lezione.getMateria();

        ArrayList<String> liberi = this.getDocentiCompletamenteLiberi(ora, giorno);
        liberi = filtraDocentiLiberi(liberi);

        System.out.println("Cerca ore libere pagamento - Ora: " + ora + ", Docenti completamente liberi (filtrati): " + liberi);

        if (liberi.isEmpty()) {
            System.out.println("Nessun docente disponibile per ore libere a pagamento");
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Nessun sostituto disponibile (ore libere a pagamento)", null, 5, COLORE_NESSUNO);
        }

        // Priorità a chi ha lezione ADIACENTE
        String sostitutoPrioritario = this.cercaDocenteConLezioneAdiacente(liberi, ora, giorno);
        if (sostitutoPrioritario != null) {
            System.out.println("Trovato docente con lezione adiacente: " + sostitutoPrioritario);
            return new Sostituzione(ora, docenteAssente, materia, classe,
                    "Sostituito da " + sostitutoPrioritario + " (ore libere a pagamento - con lezione adiacente)",
                    sostitutoPrioritario, 5, COLORE_PAGAMENTO);
        }

        // Qualsiasi docente libero
        System.out.println("Trovato docente qualsiasi per ore libere: " + liberi.get(0));
        return new Sostituzione(ora, docenteAssente, materia, classe,
                "Sostituito da " + liberi.get(0) + " (ore libere a pagamento)",
                liberi.get(0), 5, COLORE_PAGAMENTO);
    }

    private List<String> getOreLezione(Lezione lezione) {
        List<String> ore = new ArrayList<>();
        String oraInizio = lezione.getOrarioInizio();

        String durataString = lezione.getDurata();
        int durata = 1;

        try {
            if (durataString != null && durataString.contains("h")) {
                durata = Integer.parseInt(durataString.substring(0, durataString.indexOf('h')));
            } else if (durataString != null) {
                durata = Integer.parseInt(durataString);
            }
        } catch (NumberFormatException e) {
            durata = 1;
        }

        if (durata > 1) {
            String[] tutteOre = {"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};
            int indexInizio = Arrays.asList(tutteOre).indexOf(oraInizio);
            if (indexInizio >= 0) {
                for (int i = indexInizio; i < Math.min(indexInizio + durata, tutteOre.length); i++) {
                    ore.add(tutteOre[i]);
                }
            }
        } else {
            ore.add(oraInizio);
        }

        return ore;
    }

    private List<Sostituzione> ottimizzaSostituzioniOrizzontale(List<Sostituzione> sostituzioni, String giorno) {
        Map<String, List<Sostituzione>> sostituzioniPerOra = sostituzioni.stream()
                .collect(Collectors.groupingBy(Sostituzione::getOra));

        List<Sostituzione> risultato = new ArrayList<>();

        for (Map.Entry<String, List<Sostituzione>> entry : sostituzioniPerOra.entrySet()) {
            String ora = entry.getKey();
            List<Sostituzione> sostInOra = entry.getValue();

            System.out.println("=== OTTIMIZZAZIONE ORA " + ora + " ===");
            List<Sostituzione> sostituzioniOttimizzate = this.ottimizzaAssegnazioniInOra(sostInOra, ora, giorno);
            risultato.addAll(sostituzioniOttimizzate);
        }

        return risultato;
    }

    private List<Sostituzione> ottimizzaAssegnazioniInOra(List<Sostituzione> sostituzioni, String ora, String giorno) {
        if (sostituzioni.size() <= 1) {
            return sostituzioni;
        }

        System.out.println("=== OTTIMIZZAZIONE ORA " + ora + " ===");
        System.out.println("Sostituzioni da ottimizzare: " + sostituzioni.size());

        List<Sostituzione> ottimizzate = new ArrayList<>();
        Set<String> sostitutiUtilizzati = new HashSet<>();

        // Ordina per priorità (più bassa prima) e poi per importanza della classe
        List<Sostituzione> sostituzioniPending = new ArrayList<>(sostituzioni);
        Collections.sort(sostituzioniPending, (a, b) -> {
            // Prima per priorità (ascendente - 1 è più importante di 5)
            int cmpPriorita = Integer.compare(a.getPriorita(), b.getPriorita());
            if (cmpPriorita != 0) {
                return cmpPriorita;
            }

            // Poi per classe (quinte hanno priorità più alta)
            boolean aQuinta = isClasseQuinta(a.getClasse());
            boolean bQuinta = isClasseQuinta(b.getClasse());
            if (aQuinta && !bQuinta) return -1;
            if (!aQuinta && bQuinta) return 1;

            return 0;
        });

        // LOG DELL'ORDINAMENTO
        System.out.println("Ordine di elaborazione:");
        for (int i = 0; i < sostituzioniPending.size(); i++) {
            Sostituzione s = sostituzioniPending.get(i);
            System.out.println((i+1) + ". " + s.getDocenteAssente() + " (" + s.getClasse() +
                    ") - Pri: " + s.getPriorita() + " - Sost: " +
                    (s.getSostituto() != null ? s.getSostituto() : "NESSUNO"));
        }

        // Fase 1: Assegna sostituti senza conflitti
        for (Sostituzione sost : sostituzioniPending) {
            if (sost.getSostituto() == null) {
                // Nessun sostituto assegnato, mantieni così
                ottimizzate.add(sost);
                System.out.println("Mantenuto senza sostituto: " + sost.getDocenteAssente());
                continue;
            }

            if (!sostitutiUtilizzati.contains(sost.getSostituto())) {
                // Sostituto disponibile, assegna
                sostitutiUtilizzati.add(sost.getSostituto());
                ottimizzate.add(sost);
                System.out.println("✓ Assegnato " + sost.getSostituto() + " a " + sost.getDocenteAssente() + " (" + sost.getClasse() + ")");
            } else {
                // Conflitto: cerca sostituto alternativo
                System.out.println("✗ Conflitto: " + sost.getSostituto() + " già utilizzato per " + sost.getDocenteAssente() + " (" + sost.getClasse() + ")");
                Sostituzione sostituzioneAlternativa = this.trovaSostitutoAlternativoCompleto(sost, sostitutiUtilizzati, ora, giorno);
                ottimizzate.add(sostituzioneAlternativa);
                if (sostituzioneAlternativa.getSostituto() != null) {
                    sostitutiUtilizzati.add(sostituzioneAlternativa.getSostituto());
                    System.out.println("✓ Assegnato alternativo " + sostituzioneAlternativa.getSostituto() + " a " + sost.getDocenteAssente());
                } else {
                    System.out.println("✗ Nessun sostituto alternativo trovato per " + sost.getDocenteAssente());
                }
            }
        }

        System.out.println("Sostituzioni ottimizzate: " + ottimizzate.size() + ", Sostituti utilizzati: " + sostitutiUtilizzati);
        return ottimizzate;
    }

    private Sostituzione trovaSostitutoAlternativoCompleto(Sostituzione sostOriginale, Set<String> sostitutiEsclusi, String ora, String giorno) {
        String docenteAssente = sostOriginale.getDocenteAssente();
        String classe = sostOriginale.getClasse();
        String materia = sostOriginale.getMateria();
        int prioritaOriginale = sostOriginale.getPriorita();

        // Ottieni tutti i docenti liberi escludendo quelli già utilizzati
        ArrayList<String> liberi = this.getDocentiCompletamenteLiberi(ora, giorno);
        liberi = filtraDocentiLiberi(liberi);
        liberi.removeAll(sostitutiEsclusi);

        System.out.println("Cerca alternativo per " + docenteAssente + " - Classe: " + classe +
                ", Materia: " + materia + ", Liberi disponibili: " + liberi);

        if (liberi.isEmpty()) {
            System.out.println("Nessun sostituto alternativo disponibile");
            return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                    "Nessun sostituto disponibile (conflitto orario)", null, prioritaOriginale, COLORE_NESSUNO);
        }

        // Cerca secondo la priorità originale con logica completa
        switch(prioritaOriginale) {
            case 1: // Codocenza - non dovrebbe arrivare qui
                return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                        "Nessun sostituto disponibile (conflitto codocenza)", null, 1, COLORE_NESSUNO);

            case 2: // Disposizioni
                // Stessa logica delle disposizioni ma con docenti disponibili
                String sostituto = this.cercaDocenteStessaClasse(liberi, classe);
                if (sostituto != null) {
                    System.out.println("Alternativo trovato (stessa classe): " + sostituto);
                    return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                            "Sostituito da " + sostituto + " (stessa classe - disposizione - ottimizzato)",
                            sostituto, 2, COLORE_DISPOSIZIONE_STESSA_CLASSE);
                }
                sostituto = this.cercaDocenteStessaMateria(liberi, materia);
                if (sostituto != null) {
                    System.out.println("Alternativo trovato (materia affine): " + sostituto);
                    return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                            "Sostituito da " + sostituto + " (materia affine - disposizione - ottimizzato)",
                            sostituto, 2, COLORE_MATERIA_AFFINE);
                }
                // Prendi il primo disponibile che non crei conflitti
                System.out.println("Alternativo trovato (qualsiasi): " + liberi.get(0));
                return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                        "Sostituito da " + liberi.get(0) + " (qualsiasi docente - disposizione - ottimizzato)",
                        liberi.get(0), 2, COLORE_DISPOSIZIONE);

            case 3: // Compresenze non quinte
                sostituto = this.cercaDocenteStessaClasse(liberi, classe);
                if (sostituto != null) {
                    System.out.println("Alternativo trovato (stessa classe compresenza): " + sostituto);
                    return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                            "Sostituito da " + sostituto + " (stessa classe - compresenza non quinta - ottimizzato)",
                            sostituto, 3, COLORE_COMPRESENZA);
                }
                sostituto = this.cercaDocenteStessaMateria(liberi, materia);
                if (sostituto != null) {
                    System.out.println("Alternativo trovato (materia affine compresenza): " + sostituto);
                    return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                            "Sostituito da " + sostituto + " (materia affine - compresenza non quinta - ottimizzato)",
                            sostituto, 3, COLORE_MATERIA_AFFINE);
                }
                sostituto = this.cercaDocenteNonQuinta(liberi);
                if (sostituto != null) {
                    System.out.println("Alternativo trovato (docente non quinta): " + sostituto);
                    return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                            "Sostituito da " + sostituto + " (docente non quinta - compresenza - ottimizzato)",
                            sostituto, 3, COLORE_COMPRESENZA);
                }
                // Se non trova nessuno dei precedenti, usa il primo disponibile
                System.out.println("Alternativo trovato (qualsiasi compresenza): " + liberi.get(0));
                return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                        "Sostituito da " + liberi.get(0) + " (compresenza non quinta - ottimizzato)",
                        liberi.get(0), 3, COLORE_COMPRESENZA);

            case 4: // Compresenze quinte
                sostituto = this.cercaDocenteStessaMateria(liberi, materia);
                if (sostituto != null) {
                    System.out.println("Alternativo trovato (materia affine quinta): " + sostituto);
                    return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                            "Sostituito da " + sostituto + " (materia affine - compresenza quinta - ottimizzato)",
                            sostituto, 4, COLORE_MATERIA_AFFINE);
                }
                System.out.println("Alternativo trovato (qualsiasi quinta): " + liberi.get(0));
                return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                        "Sostituito da " + liberi.get(0) + " (qualsiasi materia - compresenza quinta - ottimizzato)",
                        liberi.get(0), 4, COLORE_COMPRESENZA);

            case 5: // Ore libere a pagamento
                // Priorità a chi ha lezione adiacente
                String sostitutoPrioritario = this.cercaDocenteConLezioneAdiacente(liberi, ora, giorno);
                if (sostitutoPrioritario != null) {
                    System.out.println("Alternativo trovato (con lezione adiacente): " + sostitutoPrioritario);
                    return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                            "Sostituito da " + sostitutoPrioritario + " (ore libere a pagamento - con lezione adiacente - ottimizzato)",
                            sostitutoPrioritario, 5, COLORE_PAGAMENTO);
                }
                System.out.println("Alternativo trovato (qualsiasi ore libere): " + liberi.get(0));
                return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                        "Sostituito da " + liberi.get(0) + " (ore libere a pagamento - ottimizzato)",
                        liberi.get(0), 5, COLORE_PAGAMENTO);
        }

        // Fallback generico
        System.out.println("Alternativo trovato (fallback): " + liberi.get(0));
        return new Sostituzione(sostOriginale.getOra(), docenteAssente, materia, classe,
                "Sostituito da " + liberi.get(0) + " (sostituto alternativo)",
                liberi.get(0), prioritaOriginale, COLORE_PAGAMENTO);
    }

    // CLASSE SOSTITUZIONE
    private class Sostituzione {
        private String ora;
        private String docenteAssente;
        private String materia;
        private String classe;
        private String risultato;
        private String sostituto;
        private int priorita;
        private String colore;

        public Sostituzione(String ora, String docenteAssente, String materia, String classe,
                            String risultato, String sostituto, int priorita, String colore) {
            this.ora = ora;
            this.docenteAssente = docenteAssente;
            this.materia = materia;
            this.classe = classe;
            this.risultato = risultato;
            this.sostituto = sostituto;
            this.priorita = priorita;
            this.colore = colore;
        }

        public String getOra() { return ora; }
        public String getDocenteAssente() { return docenteAssente; }
        public String getMateria() { return materia; }
        public String getClasse() { return classe; }
        public String getRisultato() { return risultato; }
        public String getSostituto() { return sostituto; }
        public int getPriorita() { return priorita; }
        public String getColore() { return colore; }
    }

    public ArrayList<String> getDocentiLiberi(String ora, String giorno) {
        Set<String> docentiDisponibili = new HashSet<>();

        for (Lezione l : this.lezioni) {
            if (l.getGiorno().equalsIgnoreCase(giorno) &&
                    l.getOrarioInizio().equalsIgnoreCase(ora) &&
                    l.getMateria().equalsIgnoreCase(DISPOSIZIONE) &&
                    l.getClasse().equalsIgnoreCase(DISPOSIZIONE)) {

                for (String cognome : l.getCognome()) {
                    String docente = cognome.trim();
                    // NON aggiungere docenti assenti
                    if (!isDocenteAssente(docente)) {
                        docentiDisponibili.add(docente);
                    }
                }
            }
        }

        return new ArrayList<>(docentiDisponibili);
    }

    private ArrayList<String> getDocentiCompletamenteLiberi(String ora, String giorno) {
        Set<String> tuttiDocenti = new HashSet<>();
        Set<String> impegnati = new HashSet<>();

        for (Lezione l : this.lezioni) {
            for (String cognome : l.getCognome()) {
                String docente = cognome.trim();
                if (!isDocenteAssente(docente)) {
                    tuttiDocenti.add(docente);
                }
            }
        }

        for (Lezione l : this.lezioni) {
            if (l.getGiorno().equalsIgnoreCase(giorno) && l.getOrarioInizio().equalsIgnoreCase(ora)) {
                for (String cognome : l.getCognome()) {
                    impegnati.add(cognome.trim());
                }
            }
        }

        tuttiDocenti.removeAll(impegnati);
        return new ArrayList<>(tuttiDocenti);
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
        return classe != null && (classe.toUpperCase().contains("5"));
    }

    private boolean haLezioneInOra(String docente, String ora, String giorno) {
        for(Lezione l : this.lezioni) {
            if (l.getGiorno().equalsIgnoreCase(giorno) &&
                    l.getOrarioInizio().equalsIgnoreCase(ora) &&
                    this.isDocenteInLezione(docente, l)) {
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
        return Arrays.stream(lezione.getCognome())
                .anyMatch(cognome -> cognome.trim().equalsIgnoreCase(docente));
    }

    private String trovaCollegaCodocenza(String docenteAssente, Lezione lezione) {
        for(String collega : lezione.getCognome()) {
            if (!collega.trim().equalsIgnoreCase(docenteAssente)) {
                return collega.trim();
            }
        }
        return null;
    }

    private String cercaDocenteStessaClasse(ArrayList<String> liberi, String classe) {
        for(Lezione lezione : this.lezioni) {
            if (lezione.getClasse().equalsIgnoreCase(classe)) {
                for(String cognome : lezione.getCognome()) {
                    String docente = cognome.trim();
                    if (liberi.contains(docente) && !isDocenteAssente(docente)) {
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
                    if (liberi.contains(docente) && !isDocenteAssente(docente)) {
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
}
