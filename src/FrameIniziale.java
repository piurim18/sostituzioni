import javax.swing.*;

public class FrameIniziale extends JFrame {

    PanelIniiziale panelIniiziale = new PanelIniiziale();

    public FrameIniziale(){
        add(panelIniiziale);
        setVisible(true);
        setSize(1100,900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
