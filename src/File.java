import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class File {


public static void leggiFile()
{
    try (
            FileInputStream fis = new FileInputStream("C:/Users/teodoro.acquistapace/Downloads/OrarioDocenti_Fake.csv");
            ObjectInputStream oos = new ObjectInputStream(fis);
            ){
        ArrayList<Docente> list =  (ArrayList<Docente>) oos.readObject();
        for (Docente d : list) {
            System.out.println(d);
        }

    }
    catch (IOException | ClassNotFoundException ex) {
        System.err.println("errore nell apertura del file");
    }

}

}
