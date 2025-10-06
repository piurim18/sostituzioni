import javax.swing.*;

public class FrameIniziale extends JFrame {

    //JButton carica = new JButton("Carica");
    //JButton prosegui = new JButton("Prosegui");
    PanelIniiziale panelIniiziale = new PanelIniiziale();

    public FrameIniziale(){


        add(panelIniiziale);
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
