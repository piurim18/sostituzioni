import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class PanelIniiziale extends JPanel {
    JButton carica = new JButton("Carica");
    JButton prosegui = new JButton("Prosegui");

    //File f = new File();

    public PanelIniiziale(){
        setLayout(new GridBagLayout());
        carica.setPreferredSize(new Dimension(150, 50));
        prosegui.setPreferredSize(new Dimension(150, 50));
        GridBagConstraints b = new GridBagConstraints();


        b.gridx = 0;
        b.gridy = 0;
        add(carica,b);

        b.gridx = 0;
        b.gridy = 1;
        b.insets = new Insets(150,0,0,0);
        add(prosegui,b);

        carica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int risultato = fileChooser.showOpenDialog(PanelIniiziale.this);

                if (risultato == JFileChooser.APPROVE_OPTION) {
                    File fileSelezionato = fileChooser.getSelectedFile();

                    try {
                        LettoreFile.leggiFile(fileSelezionato);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });


        prosegui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrarioDocente();
                //new FrameSecondo();
                //new FrameDocenti();
            }
        });


    }
}
