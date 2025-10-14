import javax.swing.*;

public class FrameSceltaOrario extends JFrame {


    public FrameSceltaOrario() {
        add(new PanelScelta());
        setVisible(true);
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
