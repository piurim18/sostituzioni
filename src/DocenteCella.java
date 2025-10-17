public class DocenteCella {
    private String materia;
    private String classe;
    private String  OrarioInizio;
    private String  durata;
    private String giorno;

    public DocenteCella(String materia, String classe, String orarioInizio, String durata, String giorno) {
        this.materia = materia;
        this.classe = classe;
        OrarioInizio = orarioInizio;
        this.durata = durata;
        this.giorno = giorno;
    }

    public String getMateria() {
        return materia;
    }

    public String getClasse() {
        return classe;
    }

    public String getOrarioInizio() {
        return OrarioInizio;
    }

    public String getDurata() {
        return durata;
    }

    public String getGiorno() {
        return giorno;
    }

    @Override
    public String toString() {
        return "DocenteCella{" +
                "materia='" + materia + '\'' +
                ", classe='" + classe + '\'' +
                ", OrarioInizio='" + OrarioInizio + '\'' +
                ", durata='" + durata + '\'' +
                ", giorno='" + giorno + '\'' +
                '}';
    }
}
