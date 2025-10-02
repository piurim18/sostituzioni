import java.io.Serializable;

public class Docente implements Serializable {
    private String cognome;
    private int numero;
    private int durata;
    private String materia;
    private String classe;
    private char codocenza;
    private String giorno;
    private int orarioInizio;


    public Docente(String cognome, int numero, String materia, char codocenza, int durata, String classe, String giorno, int orarioInizio) {
        this.cognome = cognome;
        this.numero = numero;
        this.materia = materia;
        this.codocenza = codocenza;
        this.durata = durata;
        this.classe = classe;
        this.giorno = giorno;
        this.orarioInizio = orarioInizio;
    }

    public String getCognome() {
        return cognome;
    }

    public int getOrarioInizio() {
        return orarioInizio;
    }

    public String getGiorno() {
        return giorno;
    }

    public char getCodocenza() {
        return codocenza;
    }

    public String getClasse() {
        return classe;
    }

    public String getMateria() {
        return materia;
    }

    public int getNumero() {
        return numero;
    }

    public int getDurata() {
        return durata;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "cognome='" + cognome + '\'' +
                ", numero=" + numero +
                ", durata=" + durata +
                ", materia='" + materia + '\'' +
                ", classe='" + classe + '\'' +
                ", codocenza=" + codocenza +
                ", giorno='" + giorno + '\'' +
                ", orarioInizio=" + orarioInizio +
                '}';
    }
}
