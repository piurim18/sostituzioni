//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrameSostituzioneCompresenza {
    private JTable table;
    private final String[] info = new String[]{"ORA", "SOSTITUTI"};
    private final String[] ore = new String[]{"08h00", "09h00", "10h00", "11h00", "12h00", "13h00"};
    private JButton back = new JButton("Indietro");
    private DefaultTableModel modello;

    public FrameSostituzioneCompresenza() {
        final JFrame frame = new JFrame("Sostituzioni Compresenza");
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        frame.setContentPane(mainPanel);
        this.modello = new DefaultTableModel(this.info, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for(String ora : this.ore) {
            Object[] riga = new Object[this.info.length];
            riga[0] = ora;

            for(int i = 1; i < this.info.length; ++i) {
                riga[i] = "";
            }

            this.modello.addRow(riga);
        }

        this.table = new JTable(this.modello);
        this.table.setFont(new Font("SansSerif", 1, 14));
        this.table.setRowHeight(75);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setResizingAllowed(false);
        this.table.setRowSelectionAllowed(false);
        this.table.setCellSelectionEnabled(false);
        mainPanel.add(new JScrollPane(this.table), "Center");
        JPanel southPanel = new JPanel();
        southPanel.add(this.back);
        mainPanel.add(southPanel, "South");
        this.back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FrameScelta();
                frame.dispose();
            }
        });
        frame.setSize(400, 600);
        frame.setLocationRelativeTo((Component)null);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}
