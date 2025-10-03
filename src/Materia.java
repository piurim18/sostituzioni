public class Materia {

    private String materia;

    public Materia(String materia) {
        this.materia = materia;
    }

    public String getMateria() {
        return materia;
    }

    @Override
    public String toString() {
        return "Materia{" + "materia='" + materia + '\'' + '}';
    }
}
