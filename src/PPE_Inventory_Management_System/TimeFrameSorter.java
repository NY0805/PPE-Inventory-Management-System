/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import com.toedter.calendar.JDateChooser;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class TimeFrameSorter {
    public static void timeFrame(JDateChooser startDate, JDateChooser endDate, JTable table) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String selectedStartDate = (startDate.getDate() != null) ? dateFormat.format(startDate.getDate()) : "";
        String selectedEndDate = (endDate.getDate() != null) ? dateFormat.format(endDate.getDate()) : "";
        if (!selectedStartDate.isEmpty() && !selectedEndDate.isEmpty()) {
            try {
                Date from = dateFormat.parse(selectedStartDate);
                Date to = dateFormat.parse(selectedEndDate);
                if (from.after(to)) {
                    JOptionPane.showMessageDialog(null, "Start date must not exceed the end date!", "Warning", JOptionPane.WARNING_MESSAGE);
                    startDate.setDate(null);
                    endDate.setDate(null);
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int columnCount = model.getColumnCount();
        model.setRowCount(0);
        ArrayList<String[]> records = FileHandling.ReadDataFromFile("transactions.txt");
        for (String[] record: records) {
            try {
                Date transactionDate = dateFormat.parse(record[2]);

                if ((selectedStartDate.isEmpty() || transactionDate.compareTo(dateFormat.parse(selectedStartDate)) >= 0) &&
                    (selectedEndDate.isEmpty() || transactionDate.compareTo(dateFormat.parse(selectedEndDate)) <= 0)) {
                    if (record.length == columnCount + 1 && (record[0].equals("Receive")) || record[0].equals("Distribute")) {
                        model.addRow(Arrays.copyOfRange(record, 1, record.length));
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
