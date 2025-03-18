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
public class SaveInventory {

    public static void supplierCodeDropdown(JComboBox<String> selectedSupplierCode, JTable supplierTable) {
        selectedSupplierCode.removeAllItems();
        selectedSupplierCode.addItem("Please Select");
        DefaultTableModel supplierModel = (DefaultTableModel) supplierTable.getModel();
        for (int i = 0; i < supplierModel.getRowCount(); i++) {
            String supplierID = supplierModel.getValueAt(i, 0).toString();
            selectedSupplierCode.addItem(supplierID);
        }
    }

    public static void SaveNewInventory(JTextField tfItemCode, JTextField tfItemName,
            JComboBox<String> selectedSupplierCode, JTextField tfQuantity, JSpinner spinnerPrice,
            JTable inventoryList) throws IOException {

        String itemCode, itemName, supplierCode, quantity, unitPrice;
        itemCode = tfItemCode.getText();
        itemName = tfItemName.getText();
        supplierCode = (String) selectedSupplierCode.getSelectedItem();
        quantity = tfQuantity.getText();
        Number value = (Number) spinnerPrice.getValue();
        double price = value.doubleValue();
        unitPrice = String.format("%.2f", price);

        String[] headers = {"Item Code", "Item Name", "Supplier Code", "Quantity(boxes)", "Price per box(RM)"};
        String[] data = {itemCode, itemName, supplierCode, quantity, unitPrice};

        ValidateEntity validate = new ValidateEntity();
        FileHandling ppeFile = new FileHandling();
        FileHandling supplierFile = new FileHandling();

        if (itemCode.trim().isEmpty() || itemName.trim().isEmpty() || supplierCode.equals("Please Select")
                || quantity.trim().isEmpty() || unitPrice.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        validate.validateID(itemCode, "ppe.txt");

        if (!validate.validateName(itemName) || !validate.validatePrice(unitPrice)) {
            JOptionPane.showMessageDialog(null, "Please enter valid information!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<String[]> ppeData = ppeFile.ReadDataFromFile("ppe.txt");
        ppeData.add(data);
        BufferedWriter writer = new BufferedWriter(new FileWriter("ppe.txt"));
        writer.close();
        for (String[] ppe : ppeData) {
            ppeFile.WriteDataToFile("ppe.txt", headers, ppe);

        }
        ArrayList<String[]> supplierData = supplierFile.ReadDataFromFile("suppliers.txt");  
        for (String[] supplier : supplierData) {
            if (supplier[0].equals(supplierCode.trim())) {
                if (supplier[5].length() >= 1 && !supplier[5].equals("NULL")) {
                    supplier[5] += ", " + itemCode;
                } else {
                    supplier[5] = itemCode;
                }                
            }
        }  
        System.out.println("updated:");
        BufferedWriter supplierWriter = new BufferedWriter(new FileWriter("suppliers.txt", false));
        for (String[] supplier : supplierData) {
            supplierFile.WriteDataToFile("suppliers.txt", 
                                    new String[]{"Supplier ID", "Supplier Name", "Supplier Contact", "Supplier Email", "Supplier Address", "PPE Supplies"}, 
                                    supplier);
            System.out.println(Arrays.toString(supplier));
        }
        supplierWriter.close();
        
        JOptionPane.showMessageDialog(null, "PPE added successfully!");

        tfItemCode.setText("");
        tfItemName.setText("");
        selectedSupplierCode.setSelectedIndex(0);
        tfQuantity.setText("");
        spinnerPrice.setValue(0.00);

        DefaultTableModel model = (DefaultTableModel) inventoryList.getModel();
        model.setRowCount(0);
        for (String[] rowData : ppeData) {
            if (rowData.length == 5) {
                model.addRow(rowData);
            } else {
                System.err.println("skipping record: " + Arrays.toString(rowData));
            }
        }
    }

}
