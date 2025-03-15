/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class SaveEditInventory {
    public static void saveEditInventory(JComboBox<String> selectedItemCode, JTextField tfItemName,
            JComboBox<String> selectedSupplierCode, JTextField tfQuantity, JSpinner spinnerPrice, 
            JTable inventoryList) throws IOException {
        
        String itemCode, itemName, supplierCode, quantity, unitPrice;
        itemCode = (String)selectedItemCode.getSelectedItem();
        itemName = tfItemName.getText();
        supplierCode = (String)selectedSupplierCode.getSelectedItem();
        quantity = tfQuantity.getText();
        Number value = (Number) spinnerPrice.getValue();
        double price = value.doubleValue();
        unitPrice = String.format("%.2f", price);
        
        String[] headers = {"Item Code", "Item Name", "Supplier Code", "Quantity(boxes)", "Price per box(RM)"};
 
        ValidateEntity validate = new ValidateEntity();
        FileHandling ppeFile = new FileHandling();
        FileHandling supplierFile = new FileHandling();
        
        if (itemCode.equals("Please Select") || itemName.trim().isEmpty() || 
                supplierCode.equals("Please Select") || 
                quantity.trim().isEmpty() || unitPrice.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);            
            return;
        }       
        if (!validate.validateName(itemName) || !validate.validatePrice(unitPrice)) {
            JOptionPane.showMessageDialog(null, "Please enter valid information!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        ArrayList<String[]> ppeData = ppeFile.ReadDataFromFile("ppe.txt");
        String previousSupplier = "";
        for (String[] ppe : ppeData) {
            if (ppe[0].equals(itemCode)) {
                previousSupplier = ppe[2];
                break;
            }
        }        
        if (!previousSupplier.equals(supplierCode) && !previousSupplier.equals("NULL")) {
            int confirm = JOptionPane.showConfirmDialog(null, 
                    "One item can only has one supplier. Are you sure you want to change supplier for " +  itemCode + "?", 
                    "Confirm changes", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }        
        for (int i = 0; i < ppeData.size(); i++) {
            if (ppeData.get(i)[0].equals(itemCode)) {
                ppeData.get(i)[1] = itemName;
                ppeData.get(i)[2] = supplierCode;
                ppeData.get(i)[3] = quantity;
                ppeData.get(i)[4] = unitPrice;
                break;
            }
        }        
        BufferedWriter writer = new BufferedWriter(new FileWriter("ppe.txt"));
        writer.close();
        for (String[] ppe: ppeData) {
            ppeFile.WriteDataToFile("ppe.txt", headers, ppe);

        }
        
//      ==============================================================================
        
        ArrayList<String[]> supplierData = supplierFile.ReadDataFromFile("suppliers.txt");
        if (!previousSupplier.equals("NULL")) {
            for (int i = 0; i < supplierData.size(); i++) {
                if (supplierData.get(i)[0].equals(previousSupplier)) {
                    String currentPPE = supplierData.get(i)[5];

                    // Remove only the selected item
                    if (currentPPE.contains(",")) {
                        currentPPE = currentPPE.replace(", " + itemCode, "").replace(itemCode + ", ", "").replace(itemCode, "");
                    } else if (currentPPE.equals(itemCode)) {
                        currentPPE = "NULL";
                    }

                    supplierData.get(i)[5] = currentPPE;
                }
            }
        }
        for (int i = 0; i < supplierData.size(); i++) {
            if (supplierData.get(i)[0].equals(supplierCode)) {
                String currentPPE = supplierData.get(i)[5];
                if (currentPPE.equals("NULL")) {
                    supplierData.get(i)[5] = itemCode;
                } else if (!currentPPE.contains(itemCode)) {
                    supplierData.get(i)[5] += ", " + itemCode;
                }
            }
        }
        String[] supplierHeaders = {"Supplier ID", "Supplier Name", "Supplier Contact", "Supplier Email", "Supplier Address", "PPE Supplies"};
        BufferedWriter supplierWriter = new BufferedWriter(new FileWriter("suppliers.txt"));
        supplierWriter.close();
        for (String[] supplier : supplierData) {
            supplierFile.WriteDataToFile("suppliers.txt", supplierHeaders, supplier);
        }
        
        JOptionPane.showMessageDialog(null, "PPE updated successfully!");
        
        selectedItemCode.setSelectedIndex(0);
        tfItemName.setText(""); 
        selectedSupplierCode.setSelectedIndex(0);
        tfQuantity.setText("");
        spinnerPrice.setValue(0.0);
        
        DefaultTableModel model = (DefaultTableModel) inventoryList.getModel();
        model.setRowCount(0);
        for (String[] rowData: ppeData) {
            if (rowData.length == 5) {
                model.addRow(rowData);
            }else {
                System.err.println("skipping record: " + Arrays.toString(rowData));
            }            
        } 
    }   
}
