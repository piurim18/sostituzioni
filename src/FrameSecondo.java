import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameSecondo {

    private JButton sostituzione = new JButton("SOSTITUZIONE");
    private JButton aggiorna = new JButton("AGGIORNA FILE");
    private JButton resoconto = new JButton("RESOCONTO FINALE");

    public FrameSecondo() {
        JFrame frame = new JFrame();

        sostituzione.setPreferredSize(new Dimension(150, 50));
        aggiorna.setPreferredSize(new Dimension(150, 50));
        resoconto.setPreferredSize(new Dimension(150, 50));


        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JPanel northPanel = new JPanel(new GridBagLayout());
        JPanel southPanel = new JPanel(new GridBagLayout());

        northPanel.add(sostituzione);
        centerPanel.add(aggiorna);
        southPanel.add(resoconto);

        mainPanel.add(northPanel,BorderLayout.NORTH);
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(southPanel,BorderLayout.SOUTH);



        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}