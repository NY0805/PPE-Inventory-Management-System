/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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
            int selectedRow = combobox.getSelectedIndex() - 1; // make it same with the biginning of index (start from 0)
            if (selectedRow >= 0) {
                label.setText(model.getValueAt(selectedRow, 2).toString());
            } else {
                label.setText("");
            }
        });
    }
    
    public static void ReceiveDateTime(JLabel lbDate, JLabel lbTime) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lbDate.setText(currentDate);
                
//        dateChooser.setDate(new Date());
        
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        lbTime.setText(currentTime);
        
    }
    
    public static void updatePPE(JComboBox<String> itemID, JSpinner quantity, JTable table, JTable transactionTable, JLabel supplierCode, JLabel receivedDate, JLabel receivedTime) throws IOException{
        
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
        String[] headers = {"Item ID", "Item Name", "Supplier ID", "Quantity(boxes)", "Unit Price"};

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
               
//      ==============================================================================================================
        
        // record transaction into txt and table
        FileHandling receiveTransactionFile = new FileHandling();
        DefaultTableModel transactionModel = (DefaultTableModel)transactionTable.getModel();
        
        String transactionID = ID_Generator.generate_id("transaction");
        String supplierCodeValue = supplierCode.getText();
        String receivedDateValue = receivedDate.getText();
        String receivedTimeValue = receivedTime.getText();
        String itemName = "";
        double unitPrice = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String tableItemID = model.getValueAt(i, 0).toString();
            if (tableItemID.equals(selectedItemID)) {
                itemName = model.getValueAt(i, 1).toString();
                unitPrice = Double.parseDouble(model.getValueAt(i, 4).toString());                
            }  
        }
        double income = unitPrice * selectedQuantity;
        
        transactionModel.addRow(new Object[] {
            transactionID, selectedItemID, itemName, supplierCodeValue,
            selectedQuantity, receivedDateValue, receivedTimeValue, income
        });
        
        String[] transactionHeaders = {"Transaction ID", "Item Code", "Item Name", "Supplier ID", "Quantity(boxes)", "Received Date", "Received Time", "Income"};
        String [] transactionData = {transactionID, selectedItemID, itemName, supplierCodeValue, String.valueOf(selectedQuantity), receivedDateValue, receivedTimeValue, String.valueOf(income)};
        
        receiveTransactionFile.WriteDataToFile("transactions.txt", transactionHeaders, transactionData);
        
        // clear all input after saving
        itemID.setSelectedIndex(0);
        quantity.setValue(0);
    }
}
    

