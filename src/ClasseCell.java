public class ClasseCell {

    String giorno;
    String OrarioInizio;
    String durata;
    String materia;


    public ClasseCell(String giorno, String OrarioInizio, String durata, String materia) {
        this.giorno = giorno;
        this.OrarioInizio = OrarioInizio;
        this.durata = durata;
        this.materia = materia;
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
