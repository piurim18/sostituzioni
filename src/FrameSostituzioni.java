//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrameSostituzioni {
    private JTable table;
    private DefaultTableModel modello;
    private final String[] info = new String[]{"ORA", "SOSTITUTI"};
    private final String[] ore = new String[]{"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};
    private JButton back = new JButton("Indietro");
    private JButton export = new JButton("Esporta in PDF");

    public FrameSostituzioni(ArrayList<String[]> sostituzioni) {
        JFrame frame = new JFrame("Sostituzioni Compresenza");
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        frame.setContentPane(mainPanel);
        this.modello = new DefaultTableModel(this.info, 0) {
        };

        for(String ora : this.ore) {
            this.modello.addRow(new Object[]{ora, ""});
        }

        this.table = new JTable(this.modello);
        this.table.setFont(new Font("SansSerif", 1, 14));
        this.table.setRowHeight(75);
        mainPanel.add(new JScrollPane(this.table), "Center");
        JPanel southPanel = new JPanel();
        southPanel.add(this.back);
        southPanel.add(this.export);
        mainPanel.add(southPanel, "South");

        for(String[] s : sostituzioni) {
            String ora = s[0];
            String sostituto = s[1];

            for(int i = 0; i < this.modello.getRowCount(); ++i) {
                if (this.modello.getValueAt(i, 0).equals(ora)) {
                    this.modello.setValueAt(sostituto, i, 1);
                    break;
                }
            }
        }

        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dioaca");
            }
        });

        this.back.addActionListener((e) -> {
            new FrameScelta();
            frame.dispose();
        });
        frame.setSize(800, 800);
        frame.setLocationRelativeTo((Component)null);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }


}
