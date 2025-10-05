import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


import java.io.*;
import java.util.ArrayList;


public class LettoreFile {

    static ArrayList<Lezione> lezioni = new ArrayList<>();

    public static void leggiFile(File percorsoLettoreFile) throws IOException {
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(percorsoLettoreFile)).build()) {
            String[] record;

            while ((record = csvReader.readNext()) != null) {
                for (String cella : record) {
                    System.out.print(cella + "\t");
                }
                System.out.println();
            }


        } catch (IOException ex) {
            System.err.println("Errore durante la lettura del file");
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (Lezione p : lezioni) {
            System.out.println(p);
        }
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
