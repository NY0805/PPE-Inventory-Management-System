/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ReceivePPE {
    public static void ReceivePPE(JComboBox combobox, JTable table) {
        
        combobox.removeAllItems();
        combobox.addItem("Please select");
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int rowcount = model.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            String itemCode = model.getValueAt(i, 0).toString();
            combobox.addItem(itemCode);
        }
        
//        ReceiveDate(lbReceiveDateInput);
    }
    
    public static void ReceiveDate(JLabel lbDate) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lbDate.setText(currentDate);
                
//        dateChooser.setDate(new Date());
        
    }
    
    public static void SaveReceivedPPE(JComboBox<String> itemID, JComboBox<String> supplierID, JSpinner quantity, JLabel totalcost, JLabel receivedDate, JTable table) throws IOException {
        String selectedItemID = (String)itemID.getSelectedItem();
        String selectedSUpplierID = (String)supplierID.getSelectedItem();
        int selectedQuantity = (int)quantity.getValue();
        String selectedDate = receivedDate.getText();
        String totalCostValue = totalcost.getText();
        
        if (selectedItemID.isEmpty() || selectedSUpplierID.isEmpty() || selectedQuantity == 0 || selectedDate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);            
//            return;
        }
        
        FileHandling receivePPEFile = new FileHandling();
        ArrayList<String[]> receivePPEData = receivePPEFile.ReadDataFromFile("transactions.txt");
        String[] headers = {"Item ID", "Supplier ID", "Quantity Received", "Received Date", "Total Cost"};
        String[] data = {selectedItemID, selectedSUpplierID, String.valueOf(selectedQuantity), selectedDate, totalCostValue};
        receivePPEData.add(data);
        
        for (String[] PPE: receivePPEData) {
            receivePPEFile.WriteDataToFile("transactions.txt", headers, PPE);

        }
        JOptionPane.showMessageDialog(null, "Received PPE has been recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.setColumnIdentifiers(headers);
        model.setRowCount(0);
        for (String[] rowData: receivePPEData) {
            if (rowData.length == 5) {
                System.out.println(Arrays.toString(rowData));
                model.addRow(rowData);
            }else {
                System.err.println("skipping record: " + Arrays.toString(rowData));
            }            
        }
    }


    
}
