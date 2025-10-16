import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;


import java.io.*;
import java.util.ArrayList;


public class LettoreFile {

    public static ArrayList<Lezione> lezioni = new ArrayList<>();

    static ArrayList<ClasseCell> classeCells = new ArrayList<>();



    public static void leggiFile(File percorsoLettoreFile) throws IOException, CsvValidationException{
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(percorsoLettoreFile))
                .withSkipLines(1)
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()){

            String[] riga;

            while ((riga = csvReader.readNext()) != null){



//                for (String dato: riga ){
//                    System.out.print(dato + "\t");
//                }

                int numero = (int) Double.parseDouble(riga[0]);
                String durata = riga[1];
                String materia = riga[2];
                String cogn = riga[3];
                String classe = riga[4];
                char codocenza = riga[5].charAt(0);
                String giorno = riga[6];
                String orarioInizio = riga[7];

                String [] cognomi = cogn.toLowerCase().split(";");

//                for (int i = 0; i < cognomi.length; i++){
//                    System.out.println(cognomi[i]);
//                }

                Lezione lezione = new Lezione(numero, durata, materia, cognomi, classe, codocenza, giorno, orarioInizio);
                lezioni.add(lezione);
                ClasseCell c = new ClasseCell(giorno,orarioInizio,durata,materia);
                classeCells.add(c);

            }

            System.out.println("\nTotale lezioni caricate: " + lezioni.size());
        }
    }


    public static ArrayList<Lezione> getOrarioDocente(String cognome) {
        ArrayList<Lezione> risultato = new ArrayList<>();
        for(Lezione l: lezioni){
            for(String c: l.getCognome()){
                if(c.equalsIgnoreCase(cognome)){
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
                ClasseCell co = new ClasseCell(
                        l.getGiorno(),
                        l.getOrarioInizio(),
                        l.getDurata(),
                        l.getMateria()
                );
                risOraClasse.add(co);

                System.out.println(
                        co.getGiorno() + " " +
                                co.getOrarioInizio() + " - " +
                                co.getDurata() + " | " +
                                co.getMateria()
                );
            }
        }

        return risOraClasse;
    }
















}
