import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PDFExporter {

    public static void esportaTabella(JFrame frame, JTable table, ArrayList<String> docentiAssenti, String giorno) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salva il file PDF");
            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection != JFileChooser.APPROVE_OPTION)
                return;

            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".pdf")) {
                filePath += ".pdf";
            }

            // Creazione documento
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Titolo
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            Paragraph titolo = new Paragraph("Sostituzioni - " + giorno, titleFont);
            titolo.setAlignment(Element.ALIGN_CENTER);
            titolo.setSpacingAfter(20);
            document.add(titolo);

            // Sottotitolo
            Font subFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Paragraph sottotitolo = new Paragraph("Docenti assenti: " + String.join(", ", docentiAssenti), subFont);
            sottotitolo.setAlignment(Element.ALIGN_CENTER);
            sottotitolo.setSpacingAfter(20);
            document.add(sottotitolo);

            // Tabella PDF
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            // Intestazioni
            for (int i = 0; i < table.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new com.itextpdf.text.Phrase(table.getColumnName(i),
                        new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell);
            }

            // Dati
            for (int r = 0; r < table.getRowCount(); r++) {
                for (int c = 0; c < table.getColumnCount(); c++) {
                    Object value = table.getValueAt(r, c);
                    pdfTable.addCell(value != null ? value.toString() : "");
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(frame, "PDF esportato correttamente in:\n" + filePath);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Errore durante l'esportazione PDF:\n" + ex.getMessage());
        }
    }
}
