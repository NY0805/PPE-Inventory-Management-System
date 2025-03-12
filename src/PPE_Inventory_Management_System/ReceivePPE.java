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
        

        combobox.addActionListener((ActionEvent e) -> {
            int selectedRow = combobox.getSelectedIndex() - 1;
            if (selectedRow >= 0) {
                label.setText(model.getValueAt(selectedRow, 2).toString());
            } else {
                label.setText("");
            }
        });
    }
        
    public static void updatePPE(JComboBox<String> itemID, JSpinner quantity,
            JDateChooser receivedDate, JSpinner receivedTime, JTable table,
            JTable transactionTable, JLabel supplierCode) throws IOException {
        
        FileHandling updatePPEFile = new FileHandling();

        String selectedItemID = (String)itemID.getSelectedItem();
        int selectedQuantity = (int)quantity.getValue();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDate = (receivedDate.getDate() != null) ? dateFormat.format(receivedDate.getDate()) : "";
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String selectedTime = timeFormat.format(receivedTime.getValue());
        
        if (selectedItemID.equals("Please select") || selectedQuantity == 0 || 
                selectedDate.isEmpty() || selectedTime.equals("00:00:00")) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (supplierCode.getText().equals("NULL")) {
            JOptionPane.showMessageDialog(null, "Item " + selectedItemID + 
                    " doesn't have supplier. Please add a supplier for " + selectedItemID + "!", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
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
        String[] headers = {"Item Code", "Item Name", "Supplier Code", "Quantity(boxes)", "Price per box(RM)"};

        for (String[] data: ppeData) {
            updatePPEFile.WriteDataToFile("ppe.txt", headers, data);
        }
        // update ppelist table
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String tableItemID = model.getValueAt(i, 0).toString();
            if (tableItemID.equals(selectedItemID)) {
                int currentQty = Integer.parseInt(model.getValueAt(i, 3).toString());
                model.setValueAt(currentQty + selectedQuantity, i, 3); // new value, row index, column index
                break;
            }
        }        
        JOptionPane.showMessageDialog(null, "PPE has been restocked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
               
//      ==============================================================================================================
        
        // record transaction into txt and table
        FileHandling receiveTransactionFile = new FileHandling();
        DefaultTableModel transactionModel = (DefaultTableModel)transactionTable.getModel();
        
        String transactionID = ID_Generator.generate_id("transaction");
        String supplierCodeValue = supplierCode.getText();
        String itemName = "";
        double unitPrice = 0;
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String tableItemID = model.getValueAt(i, 0).toString();
            if (tableItemID.equals(selectedItemID)) {
                itemName = model.getValueAt(i, 1).toString();
                unitPrice = Double.parseDouble(model.getValueAt(i, 4).toString());
            }  
        }
        
        double expenses = unitPrice * selectedQuantity;
        String formattedExpenses = String.format("%.2f", expenses);
        String transactionType = "Receive";
        
        transactionModel.addRow(new Object[] {
            transactionID, selectedItemID, itemName, supplierCodeValue,
            selectedQuantity, selectedDate, selectedTime, formattedExpenses
        });
        
        String[] transactionHeaders = {"Transaction ID", "Item Code", "Item Name", "Supplier ID", "Quantity(boxes)", "Received Date", "Received Time", "Expenses(RM)", "Transaction Type"};
        String [] transactionData = {transactionID, selectedItemID, itemName, supplierCodeValue, String.valueOf(selectedQuantity), selectedDate, selectedTime, formattedExpenses, transactionType};
        
        receiveTransactionFile.WriteDataToFile("transactions.txt", transactionHeaders, transactionData);
        
        itemID.setSelectedIndex(0);
        quantity.setValue(0);
        receivedDate.setDate(null);
        ((JSpinner.DefaultEditor) receivedTime.getEditor()).getTextField().setText("00:00:00");
    }
}
    

