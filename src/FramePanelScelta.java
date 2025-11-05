import javax.swing.*;

public class FramePanelScelta extends JFrame {
    PanelScelta panelScelta = new PanelScelta();

    public FramePanelScelta() {
        setTitle("FramePanelScelta");
        add(panelScelta);
        setVisible(true);
        setSize(800, 700);
    }
}
