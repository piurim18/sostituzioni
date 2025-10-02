import java.io.*;
import java.util.ArrayList;

public class File {


    public static void leggiFile() {
        String percorsoFile = "C:/Users/teodoro.acquistapace/Downloads/OrarioDocenti_Fake.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException ex) {
            System.err.println("Errore nell'apertura o lettura del file");
            ex.printStackTrace();
        }
    }

}
