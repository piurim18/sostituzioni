import java.io.*;

public class LettoreFile {

    public LettoreFile(){

    }

    public static void leggiFile(File percorsoLettoreFile) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(percorsoLettoreFile))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        }catch (IOException ex) {
            System.err.println("Errore durante la lettura del file");
            ex.printStackTrace();
        }
    }


    public static void leggiQuartaColonna(File percorsoLettoreFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(percorsoLettoreFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] colonne = linea.split(",");
                if (colonne.length > 3) {
                    System.out.println(colonne[3]);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }


}
