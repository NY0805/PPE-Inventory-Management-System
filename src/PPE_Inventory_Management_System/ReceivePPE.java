/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
       
    public static void ReceivePPE(JComboBox<String> combobox, JLabel label, JTable table) {
        
        combobox.removeAllItems();
        combobox.addItem("Please select");
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int rowcount = model.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            String itemCode = model.getValueAt(i, 0).toString();
            combobox.addItem(itemCode);
        }        
//        ReceiveDate(lbReceiveDateInput);

        combobox.addActionListener((ActionEvent e) -> {
            int selectedRow = combobox.getSelectedIndex() - 1;
            if (selectedRow >= 0) {
                label.setText(model.getValueAt(selectedRow, 2).toString()); // Get correct Supplier Code
            } else {
                label.setText(""); // Reset label if "Please select" is chosen
            }
        });
    }
    
    public static void ReceiveDate(JLabel lbDate) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lbDate.setText(currentDate);
                
//        dateChooser.setDate(new Date());
        
    }
    
    public static void updatePPE(JComboBox<String> itemID, JSpinner quantity, JTable table) throws IOException{
        
        FileHandling updatePPEFile = new FileHandling();

        String selectedItemID = (String)itemID.getSelectedItem();
        int selectedQuantity = (int)quantity.getValue();
        
        if (selectedItemID.equals("Please select") || selectedQuantity == 0) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        // read data from file to retrieve item quantity
        ArrayList<String[]> ppeData = updatePPEFile.ReadDataFromFile("ppe.txt");
        
        for (String[] data: ppeData) {
            if (data[0].equals(selectedItemID)) {
                int currentQty = Integer.parseInt(data[3]);
                data[3] = String.valueOf(currentQty + selectedQuantity);
                break;
            }
        }
                    
        // rewrite updated content into ppe.txt
        new FileWriter("ppe.txt", false).close();
        String[] headers = {"Item ID", "Item Name", "Supplier ID", "Quantity", "Unit Price"};

        for (String[] data: ppeData) {
            updatePPEFile.WriteDataToFile("ppe.txt", headers, data);
        }
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String tableItemID = model.getValueAt(i, 0).toString();
            if (tableItemID.equals(selectedItemID)) {
                int currentQty = Integer.parseInt(model.getValueAt(i, 3).toString());
                model.setValueAt(currentQty + selectedQuantity, i, 3); // new value, row index, column index
                break;
            }
        }        
        JOptionPane.showMessageDialog(null, "PPE restock successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
        // clear all input after saving
        itemID.setSelectedIndex(0);
        quantity.setValue(0);
        
    }
    
//    public static void SaveReceivedPPE(JComboBox<String> itemID, JComboBox<String> supplierID, JSpinner quantity, JLabel totalcost, JLabel receivedDate, JTable PPEtable) throws IOException {
//        String selectedItemID = (String)itemID.getSelectedItem();
//        String selectedSUpplierID = (String)supplierID.getSelectedItem();
//        int selectedQuantity = (int)quantity.getValue();
//        String selectedDate = receivedDate.getText();
//        String totalCostValue = totalcost.getText();
//        
//        if (selectedItemID.isEmpty() || selectedSUpplierID.isEmpty() || selectedQuantity == 0 || selectedDate.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);            
////            return;
//        }
//        
//        ArrayList<String[]> updatePPEData = updatePPEFile.ReadDataFromFile("ppe.txt");
//        String[] headers = {"Item ID", "Supplier ID", "Quantity Received", "Received Date", "Total Cost"};
//        String[] data = {selectedItemID, selectedSUpplierID, String.valueOf(selectedQuantity), selectedDate, totalCostValue};
//        receivePPEData.add(data);
//        
//        for (String[] PPE: updatePPEData) {
//            updatePPEFile.WriteDataToFile("transactions.txt", headers, PPE);
//
//        }
//        JOptionPane.showMessageDialog(null, "Received PPE has been recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//        
//        DefaultTableModel model = new DefaultTableModel();
//        table.setModel(model);
//        model.setColumnIdentifiers(headers);
//        model.setRowCount(0);
//        for (String[] rowData: receivePPEData) {
//            if (rowData.length == 5) {
//                System.out.println(Arrays.toString(rowData));
//                model.addRow(rowData);
//            }else {
//                System.err.println("skipping record: " + Arrays.toString(rowData));
//            }            
//        }
//    }

}
    

