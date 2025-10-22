import javax.swing.*;

public class FrameIniziale extends JFrame {


    PanelIniiziale panelIniiziale = new PanelIniiziale();

    public FrameIniziale(){


        add(panelIniiziale);
        setVisible(true);
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
