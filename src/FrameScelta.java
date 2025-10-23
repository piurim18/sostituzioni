import javax.swing.*;

public class FrameScelta extends JFrame {


    public FrameScelta() {
        add(new PanelScelta());
        setVisible(true);
        setSize(1000,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
