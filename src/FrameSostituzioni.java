import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class FrameSostituzioni {
    private JTable table;
    private DefaultTableModel modello;
    private final String[] ore = new String[]{"08h00", "09h00", "10h00", "11h10", "12h05", "13h00"};
    private JButton back = new JButton("Indietro");
    private JButton esportaPDFButton = new JButton("Esporta in PDF");

    private ArrayList<String[]> risultati;
    private ArrayList<String> docentiAssenti;
    private String giorno;

    private Map<Point, Color> coloriCelle = new HashMap<>();

    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    private final Color HEADER_COLOR = new Color(52, 73, 94);
    private final Color TABLE_HEADER_BG = new Color(70, 130, 180);
    private final Color TABLE_ROW_ALT = new Color(248, 250, 252);

    private final Color COLORE_COMPRESENZA = new Color(186, 104, 200); // ROSSO CHIARO
    private final Color COLORE_DISPOSIZIONE = new Color(102, 205, 170); // VERDE CHIARO
    private final Color COLORE_MATERIA_AFFINE = new Color (211,211,211);// GRIGINOO
    private final Color COLORE_STESSA_CLASSE = new Color(65, 105, 225); // BLU CHIARO
    private final Color COLORE_ORE_PAGAMENTO = new Color(255, 220, 255); // VIOLA CHIARO
    private final Color COLORE_CODOCENZA = new Color(255, 184, 77); // ARANCIONE CHIAROO
    private final Color COLORE_NESSUNO = Color.WHITE;

    public FrameSostituzioni(ArrayList<Object[]> tutteSostituzioni, ArrayList<String> docentiAssenti, String giorno) {
        this.docentiAssenti = docentiAssenti;
        this.giorno = giorno;

        JFrame frame = new JFrame("Sostituzioni Compresenza");
        frame.getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        frame.setContentPane(mainPanel);

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        String[] colonne = creaColonne(docentiAssenti);
        this.modello = new DefaultTableModel(colonne, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Tutte le celle non sono editabili
            }
        };

        for(String ora : this.ore) {
            Object[] riga = new Object[colonne.length];
            riga[0] = ora; // Prima colonna sempre l'ora
            this.modello.addRow(riga);
        }

        this.risultati = new ArrayList<>();
        this.coloriCelle.clear();

        for (Object[] docenteSostituzioni : tutteSostituzioni) {
            String docente = (String) docenteSostituzioni[0];
            ArrayList<String[]> sostituzioni = (ArrayList<String[]>) docenteSostituzioni[1];

            this.risultati.addAll(sostituzioni);

            int indiceColonna = -1;
            for (int i = 0; i < colonne.length; i++) {
                if (colonne[i].equals(docente + " (assente)")) {
                    indiceColonna = i;
                    break;
                }
            }

            if (indiceColonna != -1) {
                for (String[] sostituzione : sostituzioni) {
                    String ora = sostituzione[0];
                    String dettaglioSostituzione = sostituzione[1];
                    String coloreHex = sostituzione.length > 2 ? sostituzione[2] : "#FFFFFF";

                    // Trova la riga corrispondente all'ora
                    for (int riga = 0; riga < this.modello.getRowCount(); riga++) {
                        if (this.modello.getValueAt(riga, 0).equals(ora)) {
                            // Aggiungi il dettaglio della sostituzione nella colonna del docente
                            this.modello.setValueAt(dettaglioSostituzione, riga, indiceColonna);

                            // Memorizza il colore per questa cella
                            Color coloreCella = convertiColoreDaHex(coloreHex);
                            coloriCelle.put(new Point(riga, indiceColonna), coloreCella);
                            break;
                        }
                    }
                }
            }
        }

        this.table = new JTable(this.modello);
        styleTable();

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(BACKGROUND_COLOR);

        JPanel legendaPanel = creaLegendaPanel();
        southPanel.add(legendaPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        buttonPanel.add(createStyledButton(back, SECONDARY_COLOR));
        buttonPanel.add(createStyledButton(esportaPDFButton, ACCENT_COLOR));

        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        esportaPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PDFExporter.esportaTabella(frame, table, docentiAssenti, giorno);
            }
        });

        this.back.addActionListener((e) -> {
            new FrameSelezionaLista();
            frame.dispose();
        });

        frame.setSize(1200, 850);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private JPanel creaLegendaPanel() {
        JPanel legendaPanel = new JPanel();
        legendaPanel.setLayout(new BoxLayout(legendaPanel, BoxLayout.Y_AXIS));
        legendaPanel.setBackground(BACKGROUND_COLOR);
        legendaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel titoloLegenda = new JLabel("LEGENDA TIPI DI SOSTITUZIONE");
        titoloLegenda.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titoloLegenda.setForeground(HEADER_COLOR);
        titoloLegenda.setAlignmentX(Component.CENTER_ALIGNMENT);
        titoloLegenda.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel elementiPanel = new JPanel();
        elementiPanel.setLayout(new GridLayout(2, 3, 20, 10)); // 2 righe, 3 colonne
        elementiPanel.setBackground(BACKGROUND_COLOR);
        elementiPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // CORREZIONE: Aggiunti tutti i tipi di sostituzione con i colori corretti
        elementiPanel.add(creaElementoLegenda(COLORE_CODOCENZA, "Codocenza", "Docente già in compresenza"));
        elementiPanel.add(creaElementoLegenda(COLORE_DISPOSIZIONE, "Disposizione", "Docente in ore di disposizione"));
        elementiPanel.add(creaElementoLegenda(COLORE_MATERIA_AFFINE, "Materia affine", "Stessa materia o affine"));

        elementiPanel.add(creaElementoLegenda(COLORE_STESSA_CLASSE, "Stessa classe", "Docente della stessa classe"));
        elementiPanel.add(creaElementoLegenda(COLORE_COMPRESENZA, "Compresenza", "Compresenza standard"));
        elementiPanel.add(creaElementoLegenda(COLORE_ORE_PAGAMENTO, "Ore a pagamento", "Ore libere a pagamento"));

        legendaPanel.add(titoloLegenda);
        legendaPanel.add(elementiPanel);

        return legendaPanel;
    }

    private JPanel creaElementoLegenda(Color colore, String titolo, String descrizione) {
        JPanel elemento = new JPanel();
        elemento.setLayout(new BorderLayout(8, 5));
        elemento.setBackground(BACKGROUND_COLOR);
        elemento.setOpaque(false);
        elemento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JPanel colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(25, 25));
        colorPanel.setBackground(colore);
        colorPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(BACKGROUND_COLOR);
        textPanel.setOpaque(false);

        JLabel labelTitolo = new JLabel(titolo);
        labelTitolo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelTitolo.setForeground(Color.BLACK);

        JLabel labelDescrizione = new JLabel(descrizione);
        labelDescrizione.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        labelDescrizione.setForeground(Color.DARK_GRAY);

        textPanel.add(labelTitolo);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(labelDescrizione);

        elemento.add(colorPanel, BorderLayout.WEST);
        elemento.add(textPanel, BorderLayout.CENTER);

        return elemento;
    }

    private Color convertiColoreDaHex(String hexColor) {
        if (hexColor == null || hexColor.isEmpty() || hexColor.equals("#FFFFFF")) {
            return COLORE_NESSUNO;
        }

        try {
            if (hexColor.startsWith("#")) {
                return Color.decode(hexColor);
            } else {
                // Mappatura dai codici colore originali ai nuovi colori distinti
                switch(hexColor) {
                    case "#E8F5E8": return COLORE_CODOCENZA; // Codocenza → Arancione
                    case "#E3F2FD": return COLORE_DISPOSIZIONE; // Disposizione → Verde
                    case "#FFF3E0": return COLORE_MATERIA_AFFINE; // Materia affine → Giallo
                    case "#FCE4EC": return COLORE_COMPRESENZA; // Compresenza → Rosso
                    case "#F3E5F5": return COLORE_ORE_PAGAMENTO; // Ore pagamento → Viola
                    default:
                        // Fallback per nuovi colori
                        if (hexColor.equals("#FFE6E6")) return COLORE_COMPRESENZA;
                        if (hexColor.equals("#E6FFE6")) return COLORE_DISPOSIZIONE;
                        if (hexColor.equals("#FFFFC8")) return COLORE_MATERIA_AFFINE;
                        if (hexColor.equals("#E6F0FF")) return COLORE_STESSA_CLASSE;
                        if (hexColor.equals("#FFDCFF")) return COLORE_ORE_PAGAMENTO;
                        if (hexColor.equals("#FFC896")) return COLORE_CODOCENZA;
                        return COLORE_NESSUNO;
                }
            }
        } catch (Exception e) {
            return COLORE_NESSUNO;
        }
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel titleLabel = new JLabel("Sostituzioni Compresenza", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        infoPanel.setBackground(Color.WHITE);

        JLabel giornoLabel = new JLabel("Giorno: " + giorno);
        giornoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        giornoLabel.setForeground(Color.BLACK);

        JLabel docentiLabel = new JLabel("Docenti assenti: " + docentiAssenti.size());
        docentiLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        docentiLabel.setForeground(Color.BLACK);

        infoPanel.add(giornoLabel);
        infoPanel.add(docentiLabel);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(infoPanel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private void styleTable() {
        this.table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        this.table.setRowHeight(30);
        this.table.setShowGrid(true);
        this.table.setGridColor(new Color(240, 240, 240));
        this.table.setSelectionBackground(new Color(220, 240, 255));
        this.table.setSelectionForeground(Color.BLACK);

        JTableHeader header = this.table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        this.table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setForeground(Color.BLACK);

                if (column == 0) {
                    c.setBackground(new Color(240, 245, 250));
                    ((JLabel) c).setFont(new Font("Segoe UI", Font.BOLD, 13));
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    Point cellPoint = new Point(row, column);
                    Color coloreSostituzione = coloriCelle.get(cellPoint);

                    if (coloreSostituzione != null) {
                        c.setBackground(coloreSostituzione);
                    } else if (value != null && !value.toString().isEmpty() && !value.toString().equals("-")) {
                        c.setBackground(new Color(230, 245, 230));
                    } else {
                        if (row % 2 == 0) {
                            c.setBackground(Color.WHITE);
                        } else {
                            c.setBackground(TABLE_ROW_ALT);
                        }
                    }

                    ((JLabel) c).setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
                }

                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
                return c;
            }
        });
    }

    private JButton createStyledButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker()),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
                button.setForeground(Color.BLACK); // Mantieni testo nero anche all'hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setForeground(Color.BLACK); // Mantieni testo nero
            }
        });

        return button;
    }


    private String[] creaColonne(ArrayList<String> docentiAssenti) {
        String[] colonne = new String[docentiAssenti.size() + 1];
        colonne[0] = "ORA";

        for (int i = 0; i < docentiAssenti.size(); i++) {
            colonne[i + 1] = docentiAssenti.get(i) + " (assente)";
        }

        return colonne;
    }
}