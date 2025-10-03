import javax.swing.*;
import java.awt.*;

public class Fi extends JFrame {

    //JButton carica = new JButton("Carica");
    //JButton prosegui = new JButton("Prosegui");
    PanelIniiziale panelIniiziale = new PanelIniiziale();

    public Fi(){


        add(panelIniiziale);
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
