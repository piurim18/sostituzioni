import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameIniziale {

    private JButton carica = new JButton("CARICA FILE");
    private JButton avanza = new JButton("PROSEGUI");

    public FrameIniziale() {
        JFrame frame = new JFrame();

        carica.setPreferredSize(new Dimension(150, 50));
        avanza.setPreferredSize(new Dimension(150, 50));



        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(carica);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(avanza,BorderLayout.SOUTH);


        avanza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new FrameSecondo();
            }
        });


        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}