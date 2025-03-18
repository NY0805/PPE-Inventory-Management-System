/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static void DistributePPE(JComboBox<String> combobox, JLabel stockLabel, JTable tranTable) {
        
        combobox.removeAllItems();
        combobox.addItem("Please select");
        
        DefaultTableModel tranModel = (DefaultTableModel)tranTable.getModel();
        int rowcount = tranModel.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            String itemCode = tranModel.getValueAt(i, 0).toString();
            combobox.addItem(itemCode);
        }        

        combobox.addActionListener((ActionEvent e) -> {
            int selectedRow = combobox.getSelectedIndex() - 1;
            if (selectedRow >= 0 && stockLabel != null) {
                stockLabel.setText(tranModel.getValueAt(selectedRow, 3).toString());
            } else if (stockLabel != null) {
                stockLabel.setText("");
            }
        });
    }
        
    public static void updatePPE(JComboBox<String> itemID, JComboBox<String> hospitalID,
            JSpinner quantity, JTable table, JTable transactionTable, JDateChooser distributedDate,
            JSpinner distributedTime, Date initialTime) throws IOException {
        
        
        FileHandling updatePPEFile = new FileHandling();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        
        String selectedItemID = (String)itemID.getSelectedItem();
        String selectedHospitalID = (String)hospitalID.getSelectedItem();
        int selectedQuantity = (int)quantity.getValue();
        
        distributedDate.setMaxSelectableDate(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
        String selectedDate = (distributedDate.getDate() != null) ? dateFormat.format(distributedDate.getDate()) : "";
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        
        Object timeValue = distributedTime.getValue();
        String selectedTime = "00:00:00";
        if (timeValue instanceof Date) {
            String formattedTime = timeFormat.format((Date) timeValue);
            String formattedInitialTime = timeFormat.format(initialTime);
            if (!formattedTime.equals(formattedInitialTime)) {
                selectedTime = formattedTime;
            }
        } else if (timeValue instanceof String && !"00:00:00".equals(timeValue)) {
            try {
                selectedTime = timeFormat.format(timeFormat.parse((String) timeValue));
            } catch (ParseException ex) {
                Logger.getLogger(ReceivePPE.class.getName()).log(Level.SEVERE, null, ex);
                selectedTime = "00:00:00";
            }
        }
        
        if (selectedItemID.equals("Please select") || selectedHospitalID.equals("Please select") || 
                selectedQuantity == 0 || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            String tableItemID = model.getValueAt(i, 0).toString();
            if (tableItemID.equals(selectedItemID)) {
                int stock = Integer.parseInt(model.getValueAt(i, 3).toString());
                if (selectedQuantity > stock) {
                    JOptionPane.showMessageDialog(null, "Not enough stock, please select again.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                break;
            }            
        }
                
        ArrayList<String[]> ppeData = updatePPEFile.ReadDataFromFile("ppe.txt");
        
        for (String[] data: ppeData) {
            if (data[0].equals(selectedItemID)) {
                int currentQty = Integer.parseInt(data[3]);
                data[3] = String.valueOf(currentQty - selectedQuantity);
                break;
            }
        }
                    

        new FileWriter("ppe.txt", false).close();
        String[] headers = {"Item Code", "Item Name", "Supplier ID", "Quantity(boxes)", "Unit Price(RM)"};

        for (String[] data: ppeData) {
            updatePPEFile.WriteDataToFile("ppe.txt", headers, data);
        }
        
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
        
        double income = unitPrice * selectedQuantity;
        String formattedIncome = String.format("%.2f", income);
        String transactionType = "Distribute";
        
        transactionModel.addRow(new Object[] {
            transactionID, selectedDate, selectedTime, selectedItemID, itemName, selectedHospitalID,
            selectedQuantity, formattedIncome});
        
        String[] transactionHeaders = {"Transaction Type", "Transaction ID",
            "Distributed Date", "Distributed Time", "Item Code", "Item Name",
            "Hospital ID", "Quantity(boxes)", "Income"};
        
        String [] transactionData = {transactionType, transactionID,
            selectedDate, selectedTime, selectedItemID, itemName,selectedHospitalID, 
            String.valueOf(selectedQuantity), formattedIncome};
        
        distributeTransactionFile.WriteDataToFile("transactions.txt", transactionHeaders, transactionData);

        itemID.setSelectedIndex(0);
        hospitalID.setSelectedIndex(0);
        quantity.setValue(0);
        distributedDate.setDate(null);
        ((JSpinner.DefaultEditor) distributedTime.getEditor()).getTextField().setText("00:00:00");
    
    }
}
