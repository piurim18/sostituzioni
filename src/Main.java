import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        new FrameIniziale();


        ArrayList<ClasseCell> risOraClassee = new ArrayList<>();


        for (ClasseCell cooo : LettoreFile.classeCells){
            risOraClassee.add(cooo);
        }

        for (ClasseCell cooo : LettoreFile.classeCells){
            System.out.println("MAIN: "+cooo);
        }

    }
}