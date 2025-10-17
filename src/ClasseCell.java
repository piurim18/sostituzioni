public class ClasseCell {

    String giorno;
    String OrarioInizio;
    String durata;
    String materia;
    String [] cognome;


    public ClasseCell(String giorno, String orarioInizio, String durata, String materia, String[] cognome) {
        this.giorno = giorno;
        OrarioInizio = orarioInizio;
        this.durata = durata;
        this.materia = materia;
        this.cognome = cognome;
    }

    public String getMateria() {
        return materia;
    }

    public String getDurata() {
        return durata;
    }

    public String getOrarioInizio() {
        return OrarioInizio;
    }

    public String getGiorno() {
        return giorno;
    }

    public String[] getCognome() {
        return cognome;
    }

    public String stampaPerGiorno(String giornoSettimana, String orarioInizio){
        if(this.getGiorno().equals(giornoSettimana) && this.getOrarioInizio().equals(orarioInizio)) {
            return  materia;
        }
        return "";
    }


    @Override
    public String toString() {
        return "ClasseCell{" +
                "giorno='" + giorno + '\'' +
                ", OrarioInizio='" + OrarioInizio + '\'' +
                ", durata='" + durata + '\'' +
                ", materia='" + materia + '\'' +
                '}';
    }
}
