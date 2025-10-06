public class Lezione {
    private String cognome;
    private int numero;
    private String durata;
    private String materia;
    private String classe;
    private char codocenza;
    private String giorno;
    private String orarioInizio;

        //NUMERO;DURATA;MAT_NOME;DOC_COGN;CLASSE;CO-DOC.;GIORNO;O.INIZIO
    public Lezione(int numero, String durata, String materia, String cognome, String classe, char codocenza, String giorno, String orarioInizio) {
        this.cognome = cognome;
        this.numero = numero;
        this.materia = materia;
        this.codocenza = codocenza;
        this.durata = durata;
        this.classe = classe;
        this.giorno = giorno;
        this.orarioInizio = orarioInizio;
    }


    public String getOrarioInizio() {
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

    public String getDurata() {
        return durata;
    }

    public int getNumero() {
        return numero;
    }

    public String getCognome() {
        return cognome;
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
