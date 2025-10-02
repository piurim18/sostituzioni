import java.io.Serializable;
import java.util.List;

public class Docente implements Serializable {
    private String nome;
    private List<String> classi;
    private List<String> materie;
    private List<String> orariInsegnamento;
    private List<String> oreLibere;
    private List<String> oreDisponibili;


    public Docente(String nome, List<String> classi, List<String> materie, List<String> orariInsegnamento, List<String> oreLibere, List<String> oreDisponibili) {
        this.nome = nome;
        this.classi = classi;
        this.materie = materie;
        this.orariInsegnamento = orariInsegnamento;
        this.oreLibere = oreLibere;
        this.oreDisponibili = oreDisponibili;
    }


    public String getNome() {
        return nome;
    }

    public List<String> getClassi() {
        return classi;
    }

    public List<String> getMaterie() {
        return materie;
    }

    public List<String> getOrariInsegnamento() {
        return orariInsegnamento;
    }

    public List<String> getOreLibere() {
        return oreLibere;
    }

    public List<String> getOreDisponibili() {
        return oreDisponibili;
    }
}
