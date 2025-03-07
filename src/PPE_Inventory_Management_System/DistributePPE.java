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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class DistributePPE {
    public static void DistributePPE(JComboBox<String> combobox, JLabel stockLabel, JTable table) {
        
        combobox.removeAllItems();
        combobox.addItem("Please select");
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int rowcount = model.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            String itemCode = model.getValueAt(i, 0).toString();
            combobox.addItem(itemCode);
        }        

        combobox.addActionListener((ActionEvent e) -> {
            int selectedRow = combobox.getSelectedIndex() - 1; // make it same with the biginning of index (start from 0)
            if (selectedRow >= 0 && stockLabel != null) {
                stockLabel.setText(model.getValueAt(selectedRow, 3).toString());
            } else if (stockLabel != null) {
                stockLabel.setText("");
            }
        });
    }
    
    public static void DistributeDateTime(JTextField lbDate, JTextField lbTime) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lbDate.setText(currentDate);
                
//        dateChooser.setDate(new Date());
        
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        lbTime.setText(currentTime);
        
    }
    
    public static void updatePPE(JComboBox<String> itemID, JComboBox<String> hospitalID, JSpinner quantity, JTable table, JTable transactionTable, JTextField distributedDate, JTextField distributedTime) throws IOException{
        
        FileHandling updatePPEFile = new FileHandling();

        String selectedItemID = (String)itemID.getSelectedItem();
        String selectedHospitalID = (String)hospitalID.getSelectedItem();
        int selectedQuantity = (int)quantity.getValue();
        
        if (selectedItemID.equals("Please select") || selectedHospitalID.equals("Please select") || selectedQuantity == 0) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
                
        // read data from file to retrieve item quantity
        ArrayList<String[]> ppeData = updatePPEFile.ReadDataFromFile("ppe.txt");
        
        for (String[] data: ppeData) {
            if (data[0].equals(selectedItemID)) {
                int currentQty = Integer.parseInt(data[3]);
                data[3] = String.valueOf(currentQty - selectedQuantity);
                break;
            }
        }
                    
        // rewrite updated content into ppe.txt
        new FileWriter("ppe.txt", false).close();
        String[] headers = {"Item ID", "Item Name", "Supplier ID", "Quantity(boxes)", "Unit Price"};

        for (String[] data: ppeData) {
            updatePPEFile.WriteDataToFile("ppe.txt", headers, data);
        }
        // update ppelist table
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String tableItemID = model.getValueAt(i, 0).toString();
            if (tableItemID.equals(selectedItemID)) {
                int currentQty = Integer.parseInt(model.getValueAt(i, 3).toString());
                model.setValueAt(currentQty - selectedQuantity, i, 3); // new value, row index, column index
                break;
            }
        }        
        JOptionPane.showMessageDialog(null, "PPE has been distributed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        
//      ==============================================================================================================
        
        // record transaction into txt and table
        FileHandling distributeTransactionFile = new FileHandling();
        DefaultTableModel transactionModel = (DefaultTableModel)transactionTable.getModel();
        
        String transactionID = ID_Generator.generate_id("transaction");
        String distributedDateValue = distributedDate.getText();
        String distributedTimeValue = distributedTime.getText();
        String itemName = "";
        double unitPrice = 0;
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String tableItemID = model.getValueAt(i, 0).toString();
            if (tableItemID.equals(selectedItemID)) {
                itemName = model.getValueAt(i, 1).toString();
                unitPrice = Double.parseDouble(model.getValueAt(i, 4).toString());                
            }  
        }
//        double income = unitPrice * selectedQuantity;
        double income = unitPrice * selectedQuantity;
        String formattedIncome = String.format("%.2f", income);
        String transactionType = "Distribute";
        
        transactionModel.addRow(new Object[] {
            transactionID, selectedItemID, itemName, selectedHospitalID,
            selectedQuantity, distributedDateValue, distributedTimeValue, formattedIncome
        });
        
        String[] transactionHeaders = {"Transaction ID", "Item Code", "Item Name", "Hospital ID", "Quantity(boxes)", "Distributed Date", "Distributed Time", "Income", "Transaction Type"};
        String [] transactionData = {transactionID, selectedItemID, itemName, selectedHospitalID, String.valueOf(selectedQuantity), distributedDateValue, distributedTimeValue, formattedIncome, transactionType};
        
        distributeTransactionFile.WriteDataToFile("transactions.txt", transactionHeaders, transactionData);
        
        // clear all input after saving
        itemID.setSelectedIndex(0);
        hospitalID.setSelectedIndex(0);
        quantity.setValue(0);
    
    }
}
