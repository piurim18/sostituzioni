import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;

public class LettoreFile {

    public static ArrayList<Lezione> lezioni = new ArrayList<>();
    static ArrayList<ClasseCell> classeCells = new ArrayList<>();

    public static void leggiFile(File percorsoLettoreFile) throws IOException, CsvValidationException {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(percorsoLettoreFile))
                .withSkipLines(1)
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] riga;

            while ((riga = csvReader.readNext()) != null) {

                int numero = (int) Double.parseDouble(riga[0]);
                String durata = riga[1];
                String materia = riga[2];
                String cogn = riga[3];  // stringa dei docenti separati da ;
                String classe = riga[4];
                char codocenza = riga[5].charAt(0);
                String giorno = riga[6];
                String orarioInizio = riga[7];

                String[] cognomi = cogn.toLowerCase().split(";");

                // Creo la lezione con cognomi array
                Lezione lezione = new Lezione(numero, durata, materia, cognomi, classe, codocenza, giorno, orarioInizio);
                lezioni.add(lezione);

                // Creo ClasseCell con docenti come stringa
                ClasseCell c = new ClasseCell(giorno, orarioInizio, durata, materia, cogn);
                classeCells.add(c);
            }

            System.out.println("\nTotale lezioni caricate: " + lezioni.size());
        }
    }

    public static ArrayList<Lezione> getOrarioDocente(String cognome) {
        ArrayList<Lezione> risultato = new ArrayList<>();
        for (Lezione l : lezioni) {
            for (String c : l.getCognome()) {
                if (c.equalsIgnoreCase(cognome)) {
                    risultato.add(l);
                    break;
                }
            }
        }
        return risultato;
    }

    public static ArrayList<Lezione> getOrarioClasse(String classe) {
        ArrayList<Lezione> risultato = new ArrayList<>();

        for (Lezione l : lezioni) {
            if (l.getClasse().equalsIgnoreCase(classe)) {
                System.out.println(
                        l.getGiorno() + " " +
                                l.getOrarioInizio() + " - " +
                                l.getDurata() + " | " +
                                l.getMateria()
                );
                risultato.add(l);
            }
        }
        return risultato;
    }

    public static ArrayList<ClasseCell> getgetoraclassis(String classe) {
        ArrayList<ClasseCell> risOraClasse = new ArrayList<>();

        for (Lezione l : lezioni) {
            if (l.getClasse().equalsIgnoreCase(classe)) {
                // Converto l'array cognomi in stringa separata da ';'
                String docenti = String.join(";", l.getCognome());

                ClasseCell co = new ClasseCell(
                        l.getGiorno(),
                        l.getOrarioInizio(),
                        l.getDurata(),
                        l.getMateria(),
                        docenti
                );
                risOraClasse.add(co);

                System.out.println(
                        co.getGiorno() + " " +
                                co.getOrarioInizio() + " - " +
                                co.getDurata() + " | " +
                                co.getMateria() + " | " +
                                co.getDocenti()
                );
            }
        }

        return risOraClasse;
    }
}


