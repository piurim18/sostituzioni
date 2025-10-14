import javax.swing.*;
import java.awt.*;

public class  FrameClassi extends JFrame{


    JComboBox<String> classe = new JComboBox<>();
    String [] classi = {"Disposizione","1^ A MAT", "1^ C IeFP", "1^ D INF", "1^ F MEC", "1^ G AGR", "2^ A MAT", "2^ C IeFP", "2^ D INF", "2^ F MEC", "2^ G AGR", "3^ A MAT", "3^ B TUR", "3^ C IeFP", "3^ D INF", "3^ F MEC", "3^ G AGR", "4^ A MAT", "4^ B TUR", "4^ D INF", "4^ F MEC", "5^ A MAT", "5^ B TUR", "5^ D INF", "5^ F MEC"};

    public FrameClassi() {

        setTitle("Seleziona Classe");

        for (String d : classi) {
            classe.addItem(d);
        }

        setLayout(new BorderLayout());

        add(classe, BorderLayout.CENTER);

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {

        new FrameClassi();


    }
}






