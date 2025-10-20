import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameSostituzioneCompresenza {

    private JTable table;
    private final String[] info = {"ORA", "SOSTITUTI"};
    private final String[] ore = {"08h00", "09h00", "10h00", "11h00", "12h00", "13h00"};
    private JButton back = new JButton("Indietro");
    private DefaultTableModel modello;

    public FrameSostituzioneCompresenza() {
        JFrame frame = new JFrame("Sostituzioni Compresenza");
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        frame.setContentPane(mainPanel);


        modello = new DefaultTableModel(info, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (String ora : ore) {
            Object[] riga = new Object[info.length];
            riga[0] = ora;
            for (int i = 1; i < info.length; i++) riga[i] = "";
            modello.addRow(riga);
        }


        table = new JTable(modello);
        table.setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setRowHeight(75);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);


        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);


        JPanel southPanel = new JPanel();
        southPanel.add(back);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FrameScelta();
                frame.dispose();
            }
        });


        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
