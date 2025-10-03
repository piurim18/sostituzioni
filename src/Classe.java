public class Classe {
    private int anno;
    private char sezione;
    private String corso;

    public Classe(int anno, String corso, char sezione) {
        this.anno = anno;
        this.corso = corso;
        this.sezione = sezione;
    }

    public int getAnno() {
        return anno;
    }

    public String getCorso() {
        return corso;
    }

    public char getSezione() {
        return sezione;
    }

    @Override
    public String toString() {
        return "Classe{" +
                "anno=" + anno +
                ", sezione=" + sezione +
                ", corso='" + corso + '\'' +
                '}';
    }
}
