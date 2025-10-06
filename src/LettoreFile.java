import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;


import java.io.*;
import java.util.ArrayList;


public class LettoreFile {

    static ArrayList<Lezione> lezioni = new ArrayList<>();

//    public static void leggiFile(File percorsoLettoreFile) throws IOException, CsvValidationException {
//        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(percorsoLettoreFile)).build()) {
//
//
//
//            String[] riga;
//
//            while ((riga = csvReader.readNext()) != null) {
//
//                for (int i = 0; i<riga.length;i++) {
//                    String[] dati = riga[i].split(";");
//
//                    for (String dato : dati) {
//                        System.out.println(dato);
//
//                        int numero = (int) Double.parseDouble(dati[0]); // "1.0" → 1
//                        String durata = dati[1];                       // "1h00"
//                        String materia = dati[2];                      // "Lettere"
//                        String cognome = dati[3];                      // "Cognome3"
//                        String classe = dati[4];                       // "4^ B TUR"
//                        char codocenza = dati[5].charAt(0);            // "N" → 'N'
//                        String giorno = dati[6];                       // "lunedì"
//                        String orarioInizio = dati[7];                 // "08h00"
//
//                        // Crea un nuovo oggetto Lezione e aggiungilo a un ArrayList generale
//                        //Lezione lezione = new Lezione(cognome, numero, materia, codocenza, durata, classe, giorno, orarioInizio);
//                        Lezione lezione = new Lezione(numero,durata,materia,cognome,classe,codocenza,giorno,orarioInizio);
//                        lezioni.add(lezione);
//                    }
//
//
//                }
//            }
//
//        }
//
//    }




    public static void leggiFile(File percorsoLettoreFile) throws IOException, CsvValidationException {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(percorsoLettoreFile))
                .withSkipLines(1)
                .build()) {

            String[] riga;

            while ((riga = csvReader.readNext()) != null) {

                String[] dati = riga[0].split(";");

                for (String dato : dati) {
                    System.out.print(dato + "\t");
                }

                int numero = (int) Double.parseDouble(dati[0]); // "1.0" qua lo faccio diventare intero 1
                String durata = dati[1];
                String materia = dati[2];
                String cognome = dati[3];
                String classe = dati[4];
                char codocenza = dati[5].charAt(0);
                String giorno = dati[6];
                String orarioInizio = dati[7];

                Lezione lezione = new Lezione(numero, durata, materia, cognome, classe, codocenza, giorno, orarioInizio);
                lezioni.add(lezione);
            }

            System.out.println("\nTotale lezioni caricate: " + lezioni.size());
        }
    }




    public static ArrayList<Lezione> getOrarioDocente(String cognome) {
        ArrayList<Lezione> risultato = new ArrayList<>();
        for (Lezione l : lezioni) {
            if (l.getCognome().equalsIgnoreCase(cognome)) {
                risultato.add(l);
            }
        }
        return risultato;
    }


    public static void leggiQuartaColonna(File percorsoLettoreFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(percorsoLettoreFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] colonne = linea.split(";");
                if (colonne.length > 3) {
                    System.out.println(colonne[3]);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }








}
