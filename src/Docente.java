public class Docente {

    private String cognome;

    public Docente(String cognome) {
        this.cognome = cognome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "Docente{" + "cognome='" + cognome + '\'' + '}';
    }
}
