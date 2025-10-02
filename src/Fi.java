import javax.swing.*;
import java.awt.*;

public class Fi extends JFrame {

    //JButton carica = new JButton("Carica");
    //JButton prosegui = new JButton("Prosegui");
    PanelIniiziale panelIniiziale = new PanelIniiziale();

    public Fi(){
        //setLayout(new BorderLayout());
        add(panelIniiziale);
        setVisible(true);
        setSize(400,400);
    }
}
