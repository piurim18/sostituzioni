import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;


import java.io.*;
import java.util.ArrayList;


public class LettoreFile {

    static ArrayList<Lezione> lezioni = new ArrayList<>();



//    public static void leggiFile(File percorsoLettoreFile) throws IOException, CsvValidationException {
//        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(percorsoLettoreFile))
//                .withSkipLines(1)
//                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
//                .build()) {
//
//            String[] riga;
//
//            while ((riga = csvReader.readNext()) != null) {
//
//                String[] dati = riga[0].split(";");
//
//
//                for (String dato : dati) {
//                    System.out.print(dato + "\t");
//                }
//
//                int numero = (int) Double.parseDouble(dati[0]); // "1.0" qua lo faccio diventare intero 1
//                String durata = dati[1];
//                String materia = dati[2];
//                String cognome = dati[3];
//                String classe = dati[4];
//                char codocenza = dati[5].charAt(0);
//                String giorno = dati[6];
//                String orarioInizio = dati[7];
//
//                Lezione lezione = new Lezione(numero, durata, materia, cognome, classe, codocenza, giorno, orarioInizio);
//                lezioni.add(lezione);
//            }
//
//            System.out.println("\nTotale lezioni caricate: " + lezioni.size());
//        }
//    }


    public static void leggiFile(File percorsoLettoreFile) throws IOException, CsvValidationException {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(percorsoLettoreFile))
                .withSkipLines(1)
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            String[] riga;

            while ((riga = csvReader.readNext()) != null) {



                for (String dato : riga ) {
                    System.out.print(dato + "\t");
                }

                int numero = (int) Double.parseDouble(riga[0]); // "1.0" qua lo faccio diventare intero 1
                String durata = riga[1];
                String materia = riga[2];
                String cogn = riga[3];
                String classe = riga[4];
                char codocenza = riga[5].charAt(0);
                String giorno = riga[6];
                String orarioInizio = riga[7];

                String [] cognomi = cogn.toLowerCase().split(";");

                for (int i = 0; i < cognomi.length; i++) {
                    System.out.println(cognomi[i]);
                }

                Lezione lezione = new Lezione(numero, durata, materia, cognomi, classe, codocenza, giorno, orarioInizio);
                lezioni.add(lezione);
            }

            System.out.println("\nTotale lezioni caricate: " + lezioni.size());
        }
    }




    public static ArrayList<Lezione> getOrarioDocente(String cognome) {
        ArrayList<Lezione> risultato = new ArrayList<>();
        for (Lezione l : lezioni) {
            risultato.add(l);
//            if (l.getCognome().equalsIgnoreCase(cognome)) {
//                risultato.add(l);
//            }
        }
        return risultato;
    }










}
