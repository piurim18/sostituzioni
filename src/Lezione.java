public class Lezione {
    private String [] cognome;
    private int numero;
    private String durata;
    private String materia;
    private String classe;
    private char codocenza;
    private String giorno;
    private String orarioInizio;


    public Lezione(int numero, String durata, String materia, String [] cognome, String classe, char codocenza, String giorno, String orarioInizio) {
        this.cognome = cognome;
        this.numero = numero;
        this.durata = durata;
        this.materia = materia;
        this.classe = classe;
        this.codocenza = codocenza;
        this.giorno = giorno;
        this.orarioInizio = orarioInizio;
    }

    public String[] getCognome() {
        return cognome;
    }

    public int getNumero() {
        return numero;
    }

    public String getDurata() {
        return durata;
    }

    public String getMateria() {
        return materia;
    }

    public String getClasse() {
        return classe;
    }

    public char getCodocenza() {
        return codocenza;
    }

    public String getGiorno() {
        return giorno;
    }

    public String getOrarioInizio() {
        return orarioInizio;
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