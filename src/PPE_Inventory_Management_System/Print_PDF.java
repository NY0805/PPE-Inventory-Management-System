/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

/**
 *
 * @author user
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import javax.swing.*;

public class Print_PDF {
    public static void Print_PDF(JTable table, String filepath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filepath));
            document.open();

            // Title
            document.add(new Paragraph("PPE Inventory Report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Font.BOLD)));
            document.add(new Paragraph(" "));
            
            // Table
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            
            // Add column headers
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(new PdfPCell(new Phrase(table.getColumnName(i), FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
            }
            
            // Add rows from JTable
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    pdfTable.addCell(table.getValueAt(row, col).toString());
                }
            }

            document.add(pdfTable);
            document.close();
            
            JOptionPane.showMessageDialog(null, "PDF is created and saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
           
    }
}
