import java.io.*;
import java.util.*;

public class OrarioDocenti implements Serializable {
    private List<Docente> docenti;

    public OrarioDocenti() {
        this.docenti = new ArrayList<>();
    }

    public List<Docente> getDocenti() {
        return docenti;
    }

    public void aggiungiDocente(Docente docente) {
        docenti.add(docente);
    }


    public void salvaOrario(String nomeFile) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFile))) {
            oos.writeObject(this);
        }
    }


    public static OrarioDocenti caricaOrario(String nomeFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFile))) {
            return (OrarioDocenti) ois.readObject();
        }
    }
}
