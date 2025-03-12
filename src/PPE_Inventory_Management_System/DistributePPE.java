/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        
    public static void updatePPE(JComboBox<String> itemID, JComboBox<String> hospitalID,
            JSpinner quantity, JTable table, JTable transactionTable, JDateChooser distributedDate,
            JSpinner distributedTime) throws IOException {
        
        if (distributedDate.getDate() == null) {
            distributedDate.setDate(new Date());
        }
        FileHandling updatePPEFile = new FileHandling();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        
        String selectedItemID = (String)itemID.getSelectedItem();
        String selectedHospitalID = (String)hospitalID.getSelectedItem();
        int selectedQuantity = (int)quantity.getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = dateFormat.format(distributedDate.getDate());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String selectedTime = timeFormat.format(distributedTime.getValue());
        
        if (selectedItemID.equals("Please select") || selectedHospitalID.equals("Please select") || 
                selectedQuantity == 0 || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // check for the entered quantity not exceed current stock
        for (int i = 0; i < model.getRowCount(); i++) {
            if (selectedQuantity > Integer.parseInt(model.getValueAt(i, 3).toString())) {
                JOptionPane.showMessageDialog(null, "Not enough stock, please select again.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
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
        String[] headers = {"Item ID", "Item Name", "Supplier ID", "Quantity(boxes)", "Unit Price(RM)"};

        for (String[] data: ppeData) {
            updatePPEFile.WriteDataToFile("ppe.txt", headers, data);
        }
        // update ppelist table        
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
            selectedQuantity, selectedDate, selectedTime, formattedIncome
        });
        
        String[] transactionHeaders = {"Transaction Type", "Transaction ID",
            "Distributed Date", "Distributed Time", "Item Code", "Item Name",
            "Hospital ID", "Quantity(boxes)", "Income"};
        
        String [] transactionData = {transactionType, transactionID,
            selectedDate, selectedTime, selectedItemID, itemName,selectedHospitalID, 
            String.valueOf(selectedQuantity), formattedIncome};
        
        distributeTransactionFile.WriteDataToFile("transactions.txt", transactionHeaders, transactionData);
        
        // clear all input after saving
        itemID.setSelectedIndex(0);
        hospitalID.setSelectedIndex(0);
        quantity.setValue(0);
    
    }
}
